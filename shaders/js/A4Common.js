if (!Detector.webgl) {
    Detector.addGetWebGLMessage();
}

var camera, controls, scene, renderer;
var normalLines = null, group = null, backGroup = null, wireframeGroup = null;
var meshMaterial, backMaterial, wireframeMaterial, normalLineMaterial;
var lightPositions = [];
var normalLength;
var axesGroup;
var defaultTextureTarget = null;
var fixLightsToCamera = false;

function addOBJGroup(OBJgroup) {
    if (group != null) {
        scene.remove(group);
    }
    if (normalLines != null) {
        scene.remove(normalLines);
    }
    if (backGroup != null) {
        scene.remove(backGroup);
    }
    if (wireframeGroup != null) {
        scene.remove(wireframeGroup);
    }
    if (OBJgroup == null) {
        console.log("mesh is null");
        return;
    }

    group = OBJgroup;
    backGroup = OBJgroup.clone(true);
    wireframeGroup = OBJgroup.clone(true);
    scene.add(group);
    scene.add(backGroup);
    scene.add(wireframeGroup);

    normalLines = new THREE.Group();
    group.children.map(function(object) {
        THREE.BufferGeometryUtils.computeTangents(object.geometry);
        object.material = meshMaterial;
        normalLines.add(make_normals(object, normalLength));
    });
    scene.add(normalLines);

    backGroup.children.map(function(object) {
        object.material = backMaterial;
    });

    wireframeGroup.children.map(function(object) {
        object.material = wireframeMaterial;
    });

}

function commonInit() {
    // Initialization common to all parts.
    // To be initialized by specific pages:
    // * Lights
    // * Setting additional uniforms to slider values (exposure is built-in)
    // * Loading environment map if applicable
    // * Set defaultTextureTarget to the name of the uniform sampler to get images dropped on the window
    var container = document.getElementById('container');

    camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 0.01, 50);
    camera.position.z = 5;

    scene = new THREE.Scene();

    renderer = new THREE.WebGLRenderer({ antialias: true }); // WebGLRenderer CanvasRenderer
    renderer.setClearColor(0x707080);
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(window.innerWidth, window.innerHeight);
    container.appendChild(renderer.domElement);

    controls = new THREE.OrbitControls(camera, renderer.domElement);

    meshMaterial = new THREE.ShaderMaterial( {
        uniforms : {
            'lightPositions' : { 'type' : 'v3v', 'value' : []},
            'lightColors' : { 'type' : 'v3v', 'value' : []},
        },
        vertexShader: document.getElementById('vertexShader').textContent,
        fragmentShader: document.getElementById('fragmentShader').textContent,
    });
    
    meshMaterial.side = THREE.FrontSide;
    backMaterial = new THREE.MeshBasicMaterial({ color: 0xaaa000 });
    backMaterial.side = THREE.BackSide;
    wireframeMaterial = new THREE.MeshBasicMaterial({ color: 0xffffff, wireframe: true, transparent: true });
    normalLineMaterial = new THREE.LineBasicMaterial({ color: 0x0000ff, transparent: true });

    // Load a 1x1 texture into the diffuseTexture uniform
    // So the default non-textured color is not black
    // (using a data URI for a #cccccc pixel)
    var contents = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mM88x8AAp0BzdNtlUkAAAAASUVORK5CYII=';
    var loader = new THREE.TextureLoader();
    loader.load(contents, function(texture) {
        meshMaterial.uniforms['diffuseTexture'] = { type: 't', value: texture };
        meshMaterial.needsUpdate = true;
    });

    axesGroup = new THREE.AxisHelper(1.5);

    // Account for cached state in some browsers
    document.getElementById('showAxesCheckbox').onchange();
    document.getElementById('showWireframeCheckbox').onchange();
    document.getElementById('showNormalsCheckbox').onchange();
    document.getElementById('fixLightsToCameraCheckbox').onchange();
    document.getElementById('normalLengthRange').oninput();
    document.getElementById('exposureRange').oninput();

    window.addEventListener('drop', function(e) {
        e = e || event;
        e.preventDefault();
        e.stopPropagation();
        var file = e.dataTransfer.files[0];
        var fileType = file.name.split(".").pop();
        if (fileType === 'obj') {
            loadFile(file);
        } else if (file.type.match(/image.*/) && defaultTextureTarget != null) {
            loadTexture(file, defaultTextureTarget);
        } else {
            console.log("Unknown file type: " + file.name);
        }
    }, false);

    window.addEventListener('dragover', function(e) {
        e = e || event;
        e.preventDefault();
    }, false);

    window.addEventListener('resize', onWindowResize, false);
}

function make_normals(object, scale) {
    // Create a Line geometry that will draw the normals for the given object.
    // For now assumes the object is a non-indexed mesh.
    var geom = object.geometry;
    var posns = geom.getAttribute('position');
    var norms = geom.getAttribute('normal');
    if (norms) {
        var n = norms.count;

        linePosns = [];

        if (n != posns.count) {
            console.error("Help! normals and positions different length!");
        }
        for (let i = 0; i < norms.count; i++) {
            linePosns.push(posns.getX(i), posns.getY(i), posns.getZ(i));
            linePosns.push(
                posns.getX(i) + scale * norms.getX(i), 
                posns.getY(i) + scale * norms.getY(i), 
                posns.getZ(i) + scale * norms.getZ(i)
                );
        }

        var buffergeometry = new THREE.BufferGeometry();
        buffergeometry.addAttribute('position', new THREE.BufferAttribute(new Float32Array(linePosns), 3));
        var material = normalLineMaterial;
        return new THREE.LineSegments(buffergeometry, material);
    }
}

function addLight(position, color) {
    lightPositions.push(position);
    meshMaterial.uniforms.lightColors.value.push(color);
    meshMaterial.fragmentShader = '#define NUM_LIGHTS ' + lightPositions.length + '\n' + document.getElementById('fragmentShader').textContent
}

function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

function animate() {
    requestAnimationFrame(animate);
    controls.update();
    render();
}

function render() {
    meshMaterial.uniforms.lightPositions.value =
        lightPositions.map(function (p) {
            if (fixLightsToCamera) {
                return p.clone();
            } else {
                return p.clone().applyMatrix4(camera.matrixWorldInverse);
            }
        });
    renderer.render(scene, camera);
}

function loadFile(file) {
    var reader = new FileReader();
    reader.addEventListener('load', function(event) {
        var contents = event.target.result;
        var object = new THREE.OBJLoader().parse(contents);
        object.name = file.name;
        addOBJGroup(object);
    });
    reader.readAsText(file);
}

function loadTexture(file, uniformName) {
    var reader = new FileReader();
    reader.addEventListener('load', function(event) {
        var contents = event.target.result;
        var loader = new THREE.TextureLoader();
        loader.load(contents, function(texture) {
            meshMaterial.uniforms[uniformName] = { type: 't', value: texture };
            meshMaterial.needsUpdate = true;
        });
    });
    reader.readAsDataURL(file);
}

function loadEnvironmentMap(basePath) {
    var textureLoader = new THREE.CubeTextureLoader();
    textureLoader.setCrossOrigin( 'anonymous' );
    textureLoader.setPath(basePath);
    textureLoader.load([
        'posx.jpg', 'negx.jpg',
        'posy.jpg', 'negy.jpg',
        'posz.jpg', 'negz.jpg',
    ], function(texture) {
        meshMaterial.uniforms.environmentMap = { type: 't', value: texture };
    })
}

function toggleAxes(show) {
    if (show) {
        scene.add(axesGroup);
    } else {
        scene.remove(axesGroup);
    }
}

function toggleWireframe(show) {
    if (show) {
        wireframeMaterial.opacity = 1.0;
    } else {
        wireframeMaterial.opacity = 0.0;
    }
}

function toggleNormals(show) {
    if (show) {
        normalLineMaterial.opacity = 1.0;
    } else {
        normalLineMaterial.opacity = 0.0;
    }
}

function toggleFixLightsToCamera(fix) {
    fixLightsToCamera = fix;
}

function setNormalLength(length) {
    normalLength = length;
    if (group != null) {
        if (normalLines != null) {
            scene.remove(normalLines);
        }
        normalLines = new THREE.Group();
        group.children.map(function(object) {
            normalLines.add(make_normals(object, normalLength));
        });
        scene.add(normalLines);
    }
}

function setUniformLog(uniformName, logValue) {
    var value = Math.pow(2, logValue);
    if (meshMaterial != null) {
        meshMaterial.uniforms[uniformName] = { 'type' : 'f', 'value' : value };
    }
}
