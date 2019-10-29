/**
 * @author mrdoob / http://mrdoob.com/
 * @author srm / http://cs.cornell.edu/~srm
 *   modified to produce unnormalized tangents
 *   added unifyVertices
 * @author joy / http://cs.cornell.edu/~joyxiaojizhang
 *   added back tangent and tangent2 for normal mapping
 */

THREE.BufferGeometryUtils = {

	unifyVertices: function ( geometry ) {

		var index = geometry.index;
		if (index === null) {
            // Default indices.
            // Each face uses the next three vertices
            index = [];
            for (var i = 0; i < geometry.attributes.position.count; i++) {
               index.push(i);
            }
        }

		var attributes = geometry.attributes;

		if ( attributes.position === undefined ) {

			console.warn( 'THREE.BufferGeometry: Missing required attributes (index, position) in BufferGeometry.unifyVertices()' );
			return;

		}

		var positions = attributes.position.array;
		var normals = attributes.normal === undefined 
			? undefined : attributes.normal.array;
		var uvs = attributes.uv === undefined 
			? undefined : attributes.uv.array;

		var nVertices = positions.length / 3;

		function vertexKey( iv ) {
			var key = "";
			if (positions !== undefined)
				key = key + "p" + positions[3*iv] + 
					'/' + positions[3*iv+1] +
					'/' + positions[3*iv+2];
			if (normals !== undefined)
				key = key + "n" + normals[3*iv] + 
					'/' + normals[3*iv+1] +
					'/' + normals[3*iv+2];
			if (uvs !== undefined)
				key = key + "t" + uvs[2*iv] + 
					'/' + uvs[2*iv+1];
			return key;
		}

		var vertexMap = new Map();
		var vertexRenumber = [];
		var positionsNew = [], normalsNew = [], uvsNew = [];
		var nvNew = 0;
		for (var iv = 0; iv < nVertices; iv++) {
			var key = vertexKey(iv);
			var val = vertexMap.get(key);
			if ( val == undefined ) {
				positionsNew.push(positions[3*iv+0]);
				positionsNew.push(positions[3*iv+1]);
				positionsNew.push(positions[3*iv+2]);
				if ( normals !== undefined ) {
					normalsNew.push(normals[3*iv+0]);
					normalsNew.push(normals[3*iv+1]);
					normalsNew.push(normals[3*iv+2]);
				}
				if ( uvs !== undefined ) {
					uvsNew.push(uvs[2*iv+0]);
					uvsNew.push(uvs[2*iv+1]);
				}
				val = nvNew++;
				vertexMap.set(key, val);
			}
			vertexRenumber.push(val);
		}

		var indicesNew = index.map( (iv) => vertexRenumber[iv] );
		geometry.setIndex( indicesNew );
		attributes.position.setArray(new Float32Array( positionsNew ) );
		attributes.normal.setArray(new Float32Array( normalsNew ) );
		attributes.uv.setArray(new Float32Array( uvsNew ) );
	},

	computeTangents: function ( geometry ) {

		var index = geometry.index;
		var attributes = geometry.attributes;

		// based on http://www.terathon.com/code/tangent.html
		// (per vertex tangents)

		if ( attributes.position === undefined ||
			 attributes.normal === undefined ||
			 attributes.uv === undefined ) {

			console.warn( 'THREE.BufferGeometry: Missing required attributes (index, position, normal or uv) in BufferGeometry.computeTangents()' );
			return;

		}

		var indices;
        if (index === null) {
            // Default indices.
            indices = [];
            for (var i = 0; i < geometry.attributes.position.count; i++) {
               indices.push(i);
            }
        } else {
            indices = index.array;
        }
        
		var positions = attributes.position.array;
		var normals = attributes.normal.array;
		var uvs = attributes.uv.array;

		var nVertices = positions.length / 3;

		if ( attributes.tangent === undefined ) {
			geometry.addAttribute( 'tangent', new THREE.BufferAttribute( new Float32Array( 3 * nVertices ), 3 ) );
		}
        
    if ( attributes.tangent2 === undefined ) {
			geometry.addAttribute( 'tangent2', new THREE.BufferAttribute( new Float32Array( 3 * nVertices ), 3 ) );
		}

		if ( attributes.derivU === undefined ) {
			geometry.addAttribute( 'derivU', new THREE.BufferAttribute( new Float32Array( 3 * nVertices ), 3 ) );
		}
        
        if ( attributes.derivV === undefined ) {
			geometry.addAttribute( 'derivV', new THREE.BufferAttribute( new Float32Array( 3 * nVertices ), 3 ) );
		}

		var tangents = attributes.tangent.array;
    var tangents2 = attributes.tangent2.array;
		var derivU = attributes.derivU.array;
    var derivV = attributes.derivV.array;

		var tan1 = [], tan2 = [], tcounts = [];

		for ( var k = 0; k < nVertices; k ++ ) {

			tan1[ k ] = new THREE.Vector3();
			tan2[ k ] = new THREE.Vector3();
			tcounts[ k ] = 0;

		}

		var vA = new THREE.Vector3(),
			vB = new THREE.Vector3(),
			vC = new THREE.Vector3(),

			uvA = new THREE.Vector2(),
			uvB = new THREE.Vector2(),
			uvC = new THREE.Vector2(),

			sdir = new THREE.Vector3(),
			tdir = new THREE.Vector3();

		function handleTriangle( a, b, c ) {

			vA.fromArray( positions, a * 3 );
			vB.fromArray( positions, b * 3 );
			vC.fromArray( positions, c * 3 );

			uvA.fromArray( uvs, a * 2 );
			uvB.fromArray( uvs, b * 2 );
			uvC.fromArray( uvs, c * 2 );

			var x1 = vB.x - vA.x;
			var x2 = vC.x - vA.x;

			var y1 = vB.y - vA.y;
			var y2 = vC.y - vA.y;

			var z1 = vB.z - vA.z;
			var z2 = vC.z - vA.z;

			var s1 = uvB.x - uvA.x;
			var s2 = uvC.x - uvA.x;

			var t1 = uvB.y - uvA.y;
			var t2 = uvC.y - uvA.y;

			var r = 1.0 / ( s1 * t2 - s2 * t1 );

			sdir.set(
				( t2 * x1 - t1 * x2 ) * r,
				( t2 * y1 - t1 * y2 ) * r,
				( t2 * z1 - t1 * z2 ) * r
			);

			tdir.set(
				( s1 * x2 - s2 * x1 ) * r,
				( s1 * y2 - s2 * y1 ) * r,
				( s1 * z2 - s2 * z1 ) * r
			);

			tan1[ a ].add( sdir );
			tan1[ b ].add( sdir );
			tan1[ c ].add( sdir );

			tan2[ a ].add( tdir );
			tan2[ b ].add( tdir );
			tan2[ c ].add( tdir );

			tcounts[ a ]++;
			tcounts[ b ]++;
			tcounts[ c ]++;

		}

		var groups = geometry.groups;

		if ( groups.length === 0 ) {

			groups = [ {
				start: 0,
				count: indices.length
			} ];

		}

		for ( var j = 0, jl = groups.length; j < jl; ++ j ) {

			var group = groups[ j ];

			var start = group.start;
			var count = group.count;

			for ( var i = start, il = start + count; i < il; i += 3 ) {
				handleTriangle(
					indices[ i + 0 ],
					indices[ i + 1 ],
					indices[ i + 2 ]
				);

			}

		}

		var t, t2, k, w, test;
		var n = new THREE.Vector3(), n2 = new THREE.Vector3();
    var tmp = new THREE.Vector3(), tmp2 = new THREE.Vector3();
		var tPar = new THREE.Vector3(), tPar2 = new THREE.Vector3();
		var tPerp = new THREE.Vector3(), tPerp2 = new THREE.Vector3();

		function handleVertex( v ) {

			n.fromArray( normals, v * 3 );
      n2.copy( n );

			t = tan1[ v ];
			t2 = tan2[ v ];
			k = tcounts[ v ];

			// Gram-Schmidt orthogonalize

			tmp.copy( t );
			tmp.sub( n.multiplyScalar( n.dot( t ) ) ).normalize();

			// Calculate handedness

			tmp2.crossVectors( n2, t ).normalize();
			test = tmp2.dot( tan2[ v ] );
			w = ( test < 0.0 ) ? - 1.0 : 1.0;

			tangents[ v * 3 ] = tmp.x * w;
			tangents[ v * 3 + 1 ] = tmp.y * w;
			tangents[ v * 3 + 2 ] = tmp.z * w;
            
      tangents2[ v * 3 ] = tmp2.x * w;
			tangents2[ v * 3 + 1 ] = tmp2.y * w;
			tangents2[ v * 3 + 2 ] = tmp2.z * w;

			// Project tangents to be perpendicular to normal

			tPar.copy( n ).multiplyScalar( n.dot( t ) );
			tPerp.copy( t ).sub( tPar );

			derivU[ v * 3 ] = tPerp.x / k;
			derivU[ v * 3 + 1 ] = tPerp.y / k;
			derivU[ v * 3 + 2 ] = tPerp.z / k;
            
			tPar2.copy( n ).multiplyScalar( n.dot( t2 ) );
			tPerp2.copy( t2 ).sub( tPar2 );

      derivV[ v * 3 ] = tPerp2.x / k;
			derivV[ v * 3 + 1 ] = tPerp2.y / k;
			derivV[ v * 3 + 2 ] = tPerp2.z / k;
		}

		for ( var j = 0, jl = groups.length; j < jl; ++ j ) {

			var group = groups[ j ];

			var start = group.start;
			var count = group.count;

			for ( var i = start, il = start + count; i < il; i += 3 ) {
				handleVertex( indices[ i + 0 ] );
				handleVertex( indices[ i + 1 ] );
				handleVertex( indices[ i + 2 ] );

			}

		}

	}

};
