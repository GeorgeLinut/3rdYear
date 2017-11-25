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


public class Main extends SimpleApplication {

    float BODY_X=0.6f; 
    float BODY_Y=1.8f; 
    float BODY_Z=0.3f; 
    
    float HEAD_X=0.35f;
    float HEAD_Y=0.35f;
    float HEAD_Z=0.3f;
    
    float UPPER_ARM_X=0.30f; 
    float UPPER_ARM_Y=0.9f;  
    float UPPER_ARM_Z=0.30f;
    
    float LOWER_ARM_X=UPPER_ARM_X/3*2;
    float LOWER_ARM_Y=0.8f;
    float LOWER_ARM_Z=UPPER_ARM_Z/3*2;
    
    float UPPER_LEG_X=0.2f; //0.25f
    float UPPER_LEG_Y=1.3f; //0.5f
    float UPPER_LEG_Z=0.2f;
    
    float LOWER_LEG_X=UPPER_LEG_X/2;
    float LOWER_LEG_Y=1.15f;
    float LOWER_LEG_Z=UPPER_LEG_Z/2;
    
    int c=0;
    static int fps=60;
    float rotateUpperarmX=0.05f;
    float rotateUpperlegX=0.05f;
    
    Geometry body,head;
    Geometry rightUpperarm,leftUpperarm,rightLowerarm,leftLowerarm;
    Geometry rightUpperleg,leftUpperleg,rightLowerleg,leftLowerleg;
    
    Node pivot,leftShoulder,rightShoulder,leftElbow,rightElbow;
    Node hip,leftLegNode,rightLegNode,leftKnee,rightKnee;
    
    public static void main(String[] args) {
        Main app = new Main();
        
        AppSettings settings = new AppSettings(true);

        settings.setFrameRate(fps);

        app.setSettings(settings);
        
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        flyCam.setMoveSpeed(13);
        viewPort.setBackgroundColor(ColorRGBA.Gray);
                
        createBody();
        createHead();
        createUpperarms();
        createLowerarms();
        createUpperlegs();
        createLowerlegs();
        createNodes();
        createLegNodes();

        pivot.rotate(0,90,0);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(c<fps/2){
            leftShoulder.rotate(rotateUpperarmX,0.0f,0.0f);
            rightShoulder.rotate(-rotateUpperarmX,0.0f,0.0f);
            leftElbow.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightElbow.rotate(rotateUpperarmX,0.0f,0.0f);
            leftLegNode.rotate(rotateUpperarmX,0.0f,0.0f);
            rightLegNode.rotate(-rotateUpperarmX,0.0f,0.0f);
            leftKnee.rotate(rotateUpperarmX,0.0f,0.0f);
            rightKnee.rotate(rotateUpperarmX,0.0f,0.0f);
        }
        else{
            leftShoulder.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightShoulder.rotate(rotateUpperarmX,0.0f,0.0f);
            leftElbow.rotate(rotateUpperarmX,0.0f,0.0f);
            rightElbow.rotate(-rotateUpperarmX,0.0f,0.0f);
            leftLegNode.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightLegNode.rotate(rotateUpperarmX,0.0f,0.0f);
            leftKnee.rotate(-rotateUpperarmX,0.0f,0.0f);
            rightKnee.rotate(-rotateUpperarmX,0.0f,0.0f);
        }
        
        c++;
        if(c>=fps) c=0;
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
        
        
        leftShoulder.setLocalTranslation(BODY_X+UPPER_ARM_X, BODY_Y, 0);
        rightShoulder.setLocalTranslation(-BODY_X-UPPER_ARM_X, BODY_Y, 0);
        leftElbow.setLocalTranslation(0,-2*UPPER_ARM_Y,0);
        rightElbow.setLocalTranslation(0,-2*UPPER_ARM_Y,0);
        
        
        rootNode.attachChild(pivot); // put this node in the scene
        
        pivot.attachChild(body);
        pivot.attachChild(head);
        pivot.attachChild(leftShoulder); 
        pivot.attachChild(rightShoulder); 
        
        leftShoulder.attachChild(leftUpperarm);
        rightShoulder.attachChild(rightUpperarm);
        leftShoulder.attachChild(leftElbow);
        rightShoulder.attachChild(rightElbow);
        leftElbow.attachChild(leftLowerarm);
        rightElbow.attachChild(rightLowerarm);
        

        leftShoulder.rotate(-rotateUpperarmX*fps/4,0.0f,0.0f);
        rightShoulder.rotate(rotateUpperarmX*fps/4,0.0f,0.0f);
        rightElbow.rotate(-rotateUpperarmX*fps/2,0.0f,0.0f);
    }
    
    private void createLegNodes(){
        hip=new Node("hip");
        leftLegNode = new Node("legnode");
        rightLegNode = new Node("legnode");
        leftKnee = new Node("knee");
        rightKnee = new Node("knee");
        
        hip.setLocalTranslation(0, -BODY_Y, 0);
        leftLegNode.setLocalTranslation(BODY_X-UPPER_ARM_X, 0, 0);
        rightLegNode.setLocalTranslation(-BODY_X+UPPER_ARM_X, 0, 0);
        leftKnee.setLocalTranslation(0,-2*UPPER_LEG_Y,0);
        rightKnee.setLocalTranslation(0,-2*UPPER_LEG_Y,0);
        
        pivot.attachChild(hip); 
        hip.attachChild(leftLegNode); 
        hip.attachChild(rightLegNode); 
        
        leftLegNode.attachChild(leftUpperleg);
        rightLegNode.attachChild(rightUpperleg);
        leftLegNode.attachChild(leftKnee);
        rightLegNode.attachChild(rightKnee);
        leftKnee.attachChild(leftLowerleg);
        rightKnee.attachChild(rightLowerleg);
        

        leftLegNode.rotate(-rotateUpperlegX*fps/4,0.0f,0.0f);
        rightLegNode.rotate(rotateUpperlegX*fps/4,0.0f,0.0f);
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
        mat2.setColor("Color", ColorRGBA.Red);
        head.setMaterial(mat2);
        
    }
    
    private void createUpperarms(){
        Box leftUpperarmBox = new Box(UPPER_ARM_X,UPPER_ARM_Y,UPPER_ARM_Z);
        leftUpperarm = new Geometry("Box", leftUpperarmBox);
        leftUpperarm.setLocalTranslation(0,-UPPER_ARM_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Red);
        leftUpperarm.setMaterial(mat3);
        
        Box rightUpperarmBox = new Box(UPPER_ARM_X,UPPER_ARM_Y,UPPER_ARM_Z);
        rightUpperarm = new Geometry("Box", rightUpperarmBox);
        rightUpperarm.setLocalTranslation(0,-UPPER_ARM_Y,0);
        rightUpperarm.setMaterial(mat3);

    }
    
    private void createLowerarms(){
        Box leftLowerarmBox = new Box(LOWER_ARM_X,LOWER_ARM_Y,LOWER_ARM_Z);
        leftLowerarm = new Geometry("Box", leftLowerarmBox);
        leftLowerarm.setLocalTranslation(0,-LOWER_ARM_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Black);
        leftLowerarm.setMaterial(mat3);
            
        Box rightLowerarmBox = new Box(LOWER_ARM_X,LOWER_ARM_Y,LOWER_ARM_Z);
        rightLowerarm = new Geometry("Box", rightLowerarmBox);
        rightLowerarm.setLocalTranslation(0,-LOWER_ARM_Y,0);
        rightLowerarm.setMaterial(mat3);

    }
    
    private void createUpperlegs(){
                /**LEFT UPPER Leg */
        Box leftUpperlegBox = new Box(UPPER_LEG_X,UPPER_LEG_Y,UPPER_LEG_Z);
        leftUpperleg = new Geometry("Box", leftUpperlegBox);
        leftUpperleg.setLocalTranslation(0,-UPPER_LEG_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Red);
        leftUpperleg.setMaterial(mat3);
        
        Box rightUpperlegBox = new Box(UPPER_LEG_X,UPPER_LEG_Y,UPPER_LEG_Z);
        rightUpperleg = new Geometry("Box", rightUpperlegBox);
        rightUpperleg.setLocalTranslation(0,-UPPER_LEG_Y,0);
        rightUpperleg.setMaterial(mat3);

    }
    
    private void createLowerlegs(){
                /**LEFT LOWER LEGS */
        Box leftLowerlegBox = new Box(LOWER_LEG_X,LOWER_LEG_Y,LOWER_LEG_Z);
        leftLowerleg = new Geometry("Box", leftLowerlegBox);
        leftLowerleg.setLocalTranslation(0,-LOWER_LEG_Y,0);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Black);
        leftLowerleg.setMaterial(mat3);
            
        Box rightLowerlegBox = new Box(LOWER_LEG_X,LOWER_LEG_Y,LOWER_LEG_Z);
        rightLowerleg = new Geometry("Box", rightLowerlegBox);
        rightLowerleg.setLocalTranslation(0,-LOWER_LEG_Y,0);
        rightLowerleg.setMaterial(mat3);

    }
}