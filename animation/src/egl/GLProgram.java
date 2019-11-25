package egl;

import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetActiveAttrib;
import static org.lwjgl.opengl.GL20.glGetActiveUniform;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL20;

import egl.GL.GetProgramParameterName;
import egl.GL.ShaderParameter;
import egl.GL.ShaderType;
import ext.java.IOUtils;

public class GLProgram implements IDisposable {
	private static final String NON_ALLOWABLE_PREFIX = "gl_";
    private static final Pattern RGX_SEMANTIC = Pattern.compile(
        "(\\w+)\\s*;\\s*//\\s*sem\\s*\\x28\\s*(\\w+)\\s*(\\d+)\\s*\\x29\\s*$",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
        );

    private static GLProgram programInUse;
    public static GLProgram getProgramInUse() {
    	return programInUse;
    }
    public static void unuse() {
        programInUse = null;
        glUseProgram(0);
    }
    
    private int id, idVS, idFS;
    private boolean isLinked;

    private final HashMap<String, Integer> uniforms = new HashMap<>();
    private final HashMap<String, Integer> attributes = new HashMap<>();
    public final HashMap<Integer, Integer> semanticLinks = new HashMap<>();
    private HashMap<String, Integer> foundSemantics;
    
    public GLProgram(boolean init) {
        id = 0;
        idFS = 0;
        idVS = 0;

        if(init) init();
        isLinked = false;
    }
    public GLProgram() {
    	this(false);
    }
    @Override
    public void dispose() {
        if(!getIsCreated()) return;

        if(getIsInUse()) unuse();
        if(idVS != 0) {
        	glDetachShader(id, idVS);
            glDeleteShader(idVS);
            idVS = 0;
        }
        if(idFS != 0) {
        	glDetachShader(id, idFS);
            glDeleteShader(idFS);
            idFS = 0;
        }
        
        glDeleteProgram(id);

        // Reset Internal State
        id = 0;
        isLinked = false;
        if(getIsInUse()) unuse();
    }

    public int getID() {
    	return id;
    }
    public boolean getIsCreated() {
        return id != 0;
    }
    public boolean getIsLinked() {
    	return isLinked;
    }
    public boolean getIsInUse() {
        return programInUse == this;
    }
    public int getAttribute(String name) {
    	Integer i = attributes.get(name);
    	return i == null ? GL.BadAttributeLocation : i;
    }
    public int getUniform(String name) {
    	Integer i = uniforms.get(name);
    	return i == null ? GL.BadUniformLocation : i;
    }
    public int getUniformArray(String name) {
        int u = getUniform(name);
        return (u == GL.BadUniformLocation) ? getUniform(name + "[0]") : u;
    }

    public void init() {
        if(getIsCreated()) return;
        isLinked = false;
        id = glCreateProgram();
    }

    public void addShader(String materialName, int st, String src) throws Exception {
        if(getIsLinked()) throw new Exception("Program Is Already Linked");

        switch(st) {
            case ShaderType.VertexShader:
                if(idVS != 0)
                    throw new Exception("Attempting To Add Another Vertex Shader To Program");
                break;
            case ShaderType.FragmentShader:
                if(idFS != 0)
                    throw new Exception("Attempting To Add Another Fragment Shader To Program");
                break;
            default:
                throw new Exception("Shader Type Is Not Supported");
        }
        int idS = glCreateShader(st);
        glShaderSource(idS, src);
        GLError.get(st + " Source");
        glCompileShader(idS);
        GLError.get(st + " Compile");

        // Check Status
        int status = glGetShaderi(idS, ShaderParameter.CompileStatus);
        if(status != 1) {
        	String errorMsg = "Shader Compilation Error for " + materialName + " material:\r\n" + GL20.glGetShaderInfoLog(idS, 1024);
        	GLDiagnostic.writeln(errorMsg);
        	System.err.println(errorMsg);
        	glDeleteShader(idS);
            throw new Exception("Shader Had Compilation Errors");
        }

        glAttachShader(id, idS);
        GLError.get(st + " Attach");

        // If It's A Vertex Shader -> Get Semantics From Source
        if(st == ShaderType.VertexShader) {
            foundSemantics = new HashMap<String, Integer>();
            Matcher mer = RGX_SEMANTIC.matcher(src);
            while(mer.find()) {
            	MatchResult m = mer.toMatchResult();
                String attr = m.group(1);
                int sem;
                switch(m.group(2).toLowerCase()) {
                    case "position": sem = Semantic.Position; break;
                    case "normal": sem = Semantic.Normal; break;
                    case "tangent": sem = Semantic.Tangent; break;
                    case "binormal": sem = Semantic.Bitangent; break;
                    case "texcoord": sem = Semantic.TexCoord; break;
                    case "color": sem = Semantic.Color; break;
                    case "blendindices": sem = Semantic.BlendIndices; break;
                    case "blendweight": sem = Semantic.BlendWeight; break;
                    default: sem = Semantic.None; break;
                }
                int index = Integer.parseInt(m.group(3));
                sem |= index;
                foundSemantics.put(attr, sem);
            }
        }

        switch(st) {
            case ShaderType.VertexShader: idVS = idS; break;
            case ShaderType.FragmentShader: idFS = idS; break;
        }
    }
    public void addShaderFile(String materialName, int st, String file) throws Exception {
    	BufferedReader reader = IOUtils.openReaderFile(file);
    	if(reader == null) throw new Exception("Shader File \"" + file + "\" Was Not Found");

    	String src = IOUtils.readFull(reader);
    	if(src == null) throw new Exception("Shader File \"" + file + "\" Could Not Be Read");
    	
        addShader(materialName, st, src);
    }
    public void addShaderResource(String materialName, int st, String name) throws Exception {
    	BufferedReader reader = IOUtils.openReaderResource(name);
    	if(reader == null) throw new Exception("Shader Resource \"" + name + "\" Was Not Found");

    	String src = IOUtils.readFull(reader);
    	if(src == null) throw new Exception("Shader Resource \"" + name + "\" Could Not Be Read");
    	
        addShader(materialName, st, src);
    }

    public void setAttributes(HashMap<String, Integer> attr) throws Exception {
        if(getIsLinked()) throw new Exception("Program Is Already Linked");
        for(Entry<String, Integer> kvp : attr.entrySet()) {
            // Make Sure It Is A Good Binding
            String name = kvp.getKey();
            if(name.startsWith(NON_ALLOWABLE_PREFIX))
                continue;

            // Check Location
            int loc = kvp.getValue();
            if(loc < 0)
                continue;

            // Place It In
            attributes.put(name, loc);
            glBindAttribLocation(id, loc, name);
            GLError.get("Program Attr Bind");
        }
    }
    public boolean link(String materialName) {
        if(getIsLinked()) return false;

        glLinkProgram(id);
        GLError.get("Program Link");
        glValidateProgram(id);
        GLError.get("Program Validate");

        int status = glGetProgrami(id, GetProgramParameterName.LinkStatus);
        isLinked = status == 1;
        if(!isLinked) {
        	String errorMsg = "Program Link Error for " + materialName + " material:\r\n" + GL20.glGetProgramInfoLog(id, 1024);
        	GLDiagnostic.writeln(errorMsg);
        	System.err.println(errorMsg);
        }
        return isLinked;
    }
    public void initAttributes() {
        // How Many Attributes Are In The Program
        int count = glGetProgrami(id, GetProgramParameterName.ActiveAttributes);

        // Necessary Info
        String name;
        int loc;

        // Enumerate Through Attributes
        for(int i = 0; i < count; i++) {
            // Get Uniform Info
            name = glGetActiveAttrib(id, i, 1024);
            loc = glGetAttribLocation(id, name);

            // Get Rid Of System Uniforms
            if(!name.startsWith(NON_ALLOWABLE_PREFIX) && loc > -1)
                attributes.put(name, loc);
        }

        if(foundSemantics != null) {
            generateSemanticBindings(foundSemantics);
            foundSemantics = null;
        }
    }
    public void initUniforms() {
        // How Many Uniforms Are In The Program
        int count = glGetProgrami(id, GetProgramParameterName.ActiveUniforms);

        // Necessary Info
        String name;
        int loc;

        // Enumerate Through Uniforms
        for(int i = 0; i < count; i++) {
            // Get Uniform Info
        	name = glGetActiveUniform(id, i, 1024);
            loc = glGetUniformLocation(id, name);

            // Get Rid Of System Uniforms
            if(!name.startsWith(NON_ALLOWABLE_PREFIX) && loc > -1)
                uniforms.put(name, loc);
        }
    }
    
    public void generateSemanticBindings(HashMap<String, Integer> dSems) {
        for(Entry<String, Integer> kvp : dSems.entrySet()) {
        	Integer vi = attributes.get(kvp.getKey());
            if(vi != null)
                semanticLinks.put(kvp.getValue(), vi);
        }
    }

    public void use() {
        if(getIsInUse() || !isLinked) return;
        programInUse = this;
        glUseProgram(id);
    }

    public GLProgram quickCreate(String materialName, String vsFile, String fsFile, HashMap<String, Integer> attr) {
        init();
        try {
            addShaderFile(materialName, ShaderType.VertexShader, vsFile);
            addShaderFile(materialName, ShaderType.FragmentShader, fsFile);
            if(attr != null)
                setAttributes(attr);
            link(materialName);
        }
        catch(Exception e) {
        	System.err.println(e.getMessage());
            return this;
        }
        initAttributes();
        initUniforms();
        return this;
    }
    public GLProgram quickCreateSource(String materialName, String vsSrc, String fsSrc, HashMap<String, Integer> attr) {
        init();
        try {
            addShader(materialName, ShaderType.VertexShader, vsSrc);
            addShader(materialName, ShaderType.FragmentShader, fsSrc);
            if(attr != null)
                setAttributes(attr);
            link(materialName);
        }
        catch(Exception e) {
        	System.err.println(e.getMessage());
            return this;
        }
        initAttributes();
        initUniforms();
        return this;
    }
    public GLProgram quickCreateResource(String materialName, String vsRes, String fsRes, HashMap<String, Integer> attr) {
        init();
        try {
            addShaderResource(materialName, ShaderType.VertexShader, vsRes);
            addShaderResource(materialName, ShaderType.FragmentShader, fsRes);
            if(attr != null)
                setAttributes(attr);
            link(materialName);
        }
        catch(Exception e) {
        	System.err.println(e.getMessage());
            return this;
        }
        initAttributes();
        initUniforms();
        return this;
    }
    
    public void printUniforms() {
    	System.out.println(uniforms);
    }
}
