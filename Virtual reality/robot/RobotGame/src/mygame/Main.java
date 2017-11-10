package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    float BODY_X=0.6f;
    float BODY_Y=0.8f;
    float BODY_Z=0.3f;
    
    float HEAD_X=0.25f;
    float HEAD_Y=0.25f;
    float HEAD_Z=0.2f;
    
    float UPPER_ARM_X=0.15f;
    float UPPER_ARM_Y=0.3f;
    float UPPER_ARM_Z=0.15f;
    
    float LOWER_ARM_X=UPPER_ARM_X;
    float LOWER_ARM_Y=0.25f;
    float LOWER_ARM_Z=UPPER_ARM_Z;
    
    float UPPER_LEG_X=0.2f;
    float UPPER_LEG_Y=0.5f;
    float UPPER_LEG_Z=0.2f;
    
    float LOWER_LEG_X=UPPER_LEG_X;
    float LOWER_LEG_Y=0.25f;
    float LOWER_LEG_Z=UPPER_LEG_Z;
    
    
    int c=0;
    int n=30;
    float rotateUpperarmX=0.03f;
    
    Geometry body,head;
    Geometry rightUpperarm,leftUpperarm,rightLowerarm,leftLowerarm,leftUpperLeg,rightUpperLeg,leftLowerLeg,rightLowerLeg;
    
    Node pivot,leftShoulder,rightShoulder,leftElbow,rightElbow,leftHip,rightHip,leftKnee,rightKnee;
    
    public static void main(String[] args) {
        Main app = new Main();
        
        AppSettings settings = new AppSettings(true);

        settings.setFrameRate(60);

        app.setSettings(settings);
        
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        viewPort.setBackgroundColor(ColorRGBA.White);
                
        createBody();
        createHead();
        createUpperarms();
        createUpperLegs();
        createLowerarms();
        createNodes();

    }

    @Override
    public void simpleUpdate(float tpf) {
        if(c<n){
            leftShoulder.rotate(rotateUpperarmX,0.0f,0.0f);
            rightShoulder.rotate(-rotateUpperarmX,0.0f,0.0f);
            leftHip.rotate(rotateUpperarmX,0.0f,0.0f);
            rightHip.rotate(-rotateUpperarmX,0.0f,0.0f);
            leftElbow.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightElbow.rotate(rotateUpperarmX,0.0f,0.0f);
        }
        else{
            leftShoulder.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightShoulder.rotate(rotateUpperarmX,0.0f,0.0f);
            leftHip.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightHip.rotate(rotateUpperarmX,0.0f,0.0f);
            leftElbow.rotate(rotateUpperarmX,0.0f,0.0f);
            rightElbow.rotate(-rotateUpperarmX,0.0f,0.0f);
        }
        
        c++;
        if(c>=2*n) c=0;
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private void createNodes(){
        pivot = new Node("pivot");
        leftShoulder = new Node("shoulder");
        rightShoulder = new Node("shoulder");
        leftElbow = new Node("elbow");
        rightElbow = new Node("elbow");
        leftHip = new Node("hip");
        rightHip = new Node("hip");
        leftKnee = new Node("knee");
        rightKnee = new Node("knee");
        //BODY_X-UPPER_LEG_X*3/2,-UPPER_LEG_Y-BODY_Y,0
        
        
        leftShoulder.setLocalTranslation(BODY_X+UPPER_ARM_X, BODY_Y, 0);
        rightShoulder.setLocalTranslation(-BODY_X-UPPER_ARM_X, BODY_Y, 0);
        leftElbow.setLocalTranslation(0,-2*UPPER_ARM_Y,0);
        rightElbow.setLocalTranslation(0,-2*UPPER_ARM_Y,0);
        leftHip.setLocalTranslation(BODY_X-UPPER_LEG_X*3/2,0,0);
        
        
        
        rootNode.attachChild(pivot); // put this node in the scene
        
        pivot.attachChild(body);
        pivot.attachChild(head);
        pivot.attachChild(leftShoulder); 
        pivot.attachChild(rightShoulder); 
        pivot.attachChild(leftHip);
        pivot.attachChild(rightHip);
        
        leftShoulder.attachChild(leftUpperarm);
        rightShoulder.attachChild(rightUpperarm);
        leftShoulder.attachChild(leftElbow);
        rightShoulder.attachChild(rightElbow);
        leftElbow.attachChild(leftLowerarm);
        rightElbow.attachChild(rightLowerarm);
        leftHip.attachChild(leftUpperLeg);
        rightHip.attachChild(rightUpperLeg);
        
        leftShoulder.rotate(-rotateUpperarmX*n/2,0.0f,0.0f);
        leftHip.rotate(-rotateUpperarmX*n/2,0.0f,0.0f);
        leftShoulder.rotate(-rotateUpperarmX*n/2,0.0f,0.0f);
        rightShoulder.rotate(rotateUpperarmX*n/2,0.0f,0.0f);
        rightElbow.rotate(-rotateUpperarmX*n,0.0f,0.0f);
    }
    
    private void createBody(){
                /** BODY */
        Box bodyBox = new Box(BODY_X,BODY_Y,BODY_Z);
        body = new Geometry("Box", bodyBox);
        body.setLocalTranslation(new Vector3f(0,0,0));
        Material mat1 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Black);
        body.setMaterial(mat1);

    }
    
    private void createHead(){
                /** HEAD */
        Box headBox = new Box(HEAD_X,HEAD_Y,HEAD_Z);
        head = new Geometry("Box", headBox);
        head.setLocalTranslation(new Vector3f(0,BODY_Y+HEAD_Y,0));
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Green);
        head.setMaterial(mat2);
        
    }
    
    private void createUpperarms(){
                /**LEFT UPPER ARM */
        Box leftUpperarmBox = new Box(UPPER_ARM_X,UPPER_ARM_Y,UPPER_ARM_Z);
        leftUpperarm = new Geometry("Box", leftUpperarmBox);
        leftUpperarm.setLocalTranslation(0,-UPPER_ARM_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Green);
        leftUpperarm.setMaterial(mat3);
        
        /**RIGHT UPPER ARM */
        Box rightUpperarmBox = new Box(UPPER_ARM_X,UPPER_ARM_Y,UPPER_ARM_Z);
        rightUpperarm = new Geometry("Box", rightUpperarmBox);
        rightUpperarm.setLocalTranslation(0,-UPPER_ARM_Y,0);
        rightUpperarm.setMaterial(mat3);

    }
    
    private void createUpperLegs(){
        Box leftUpperLegBox = new Box(UPPER_LEG_X,UPPER_LEG_Y,UPPER_LEG_Z);
        leftUpperLeg = new Geometry("Box", leftUpperLegBox);
        leftUpperLeg.setLocalTranslation(0,-UPPER_LEG_Y-BODY_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Green);
        leftUpperLeg.setMaterial(mat3);
        
        Box rightUpperLegBox = new Box(UPPER_LEG_X,UPPER_LEG_Y,UPPER_LEG_Z);
        rightUpperLeg = new Geometry("Box", rightUpperLegBox);
        rightUpperLeg.setLocalTranslation(-BODY_X+UPPER_LEG_X*3/2,-UPPER_LEG_Y,0);
        rightUpperLeg.setMaterial(mat3);

    }
    
    private void createLowerarms(){
                /**LEFT UPPER ARM */
        Box leftLowerarmBox = new Box(LOWER_ARM_X,LOWER_ARM_Y,LOWER_ARM_Z);
        leftLowerarm = new Geometry("Box", leftLowerarmBox);
        leftLowerarm.setLocalTranslation(0,-LOWER_ARM_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Green);
        leftLowerarm.setMaterial(mat3);
        
        /**RIGHT UPPER ARM */
        Box rightLowerarmBox = new Box(LOWER_ARM_X,LOWER_ARM_Y,LOWER_ARM_Z);
        rightLowerarm = new Geometry("Box", rightLowerarmBox);
        rightLowerarm.setLocalTranslation(0,-LOWER_ARM_Y,0);
        rightLowerarm.setMaterial(mat3);

    }
}
