var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight,0.1, 1000);

camera.position.z = 2;

var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);
var controls = new THREE.OrbitControls(camera, renderer.domElement);

controls.enableDamping = true;
controls.dampingFactor = 0.25;
controls.enableZoom = true;

//Setup lighting
var keyLight = new THREE.DirectionalLight(0xffffff, 1.0);
keyLight.position.set(-50, 0, 50);

var fillLight = new THREE.DirectionalLight(0xffffff, 1.0);
fillLight.position.set(50, 0, 50);

var backLight = new THREE.DirectionalLight(0xffffff, 1.0);
backLight.position.set(50, 0, -50).normalize();

var topLigh = new THREE.DirectionalLight(0xffffff, 0.7);
topLigh.position.set(0, 50, 0);

//Add lighting to the scene
scene.add(keyLight);
scene.add(fillLight);
scene.add(backLight);
scene.add(topLigh);

//Set backgound color
scene.background = new THREE.Color(0xefefef);

var mtlLoader = new THREE.MTLLoader();


function loadModel(path, model) {
  try {
    var selectedObject = scene.getObjectByName(name);
    if (selectedObject) {
      scene.remove(selectedObject);
    }
  } catch (error) {
    console.error(error.message);
  }
  mtlLoader.setTexturePath(path);
  mtlLoader.setPath(path);
  mtlLoader.load(`${model}.mtl`, function (materials) {
    materials.preload();
    var objLoader = new THREE.OBJLoader();
    objLoader.setMaterials(materials);
    objLoader.setPath(path);
    objLoader.load(`${model}.obj`, function (object) {
      scene.add(object);
    });
  });
}

function animate() {
  requestAnimationFrame(animate);
  controls.update();
  renderer.render(scene, camera);
};

animate();


