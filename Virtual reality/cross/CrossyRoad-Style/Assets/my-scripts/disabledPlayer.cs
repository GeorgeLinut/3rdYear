using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class disabledPlayer : MonoBehaviour {

	public GameObject strip1;
	public GameObject strip2;
	public GameObject strip3;
	public GameObject strip4;
	public GameObject strip5;
	public GameObject strip6;
	public GameObject strip7;
	public GameObject strip8;
	public GameObject strip9;
	public GameObject strip10;
	public GameObject strip11;
	public GameObject strip12;
	public GameObject strip13;
	public GameObject strip14;

	public GameObject BoundaryLeft;
	public GameObject BoundaryRight;
	public GameObject mesh;
	public GameObject startPanel;
	public GameObject gameOverPanel;

	public Text scoreText;
	private float midPoint;
	private int highScore=0;
	private float initialPositionY;
	public float jumpHeightIncrement = 4f;
	private bool isDead = false;
	private bool isPlayingDeadAnimation = false;
	private float jumpDistanceZ = 7.0f;
	private bool gamePlay = false;

	private List<GameObject> strips;
	int stripsCurrentIndex;
	float jumpOffsetX = 1.5f;

	public Vector3 jumpTargetLocation;
	public float movingSpeed = 10f;

	public GameObject[] poolStripsPrefabs;


	bool isJumpingUp;
	bool isJumpingDown;
	bool isJumpingLeft;
	bool isJumpingRight;

	// Use this for initialization
	void Start () {
		strips = new List<GameObject> ();
		isJumpingUp = isJumpingRight=isJumpingLeft=isJumpingDown=false;
		strips.Add (strip1);
		strips.Add (strip2);
		strips.Add (strip3);
		strips.Add (strip4);
		strips.Add (strip5);
		strips.Add (strip6);
		strips.Add (strip7);
		strips.Add (strip8);
		strips.Add (strip9);
		strips.Add (strip10);
		strips.Add (strip11);
		strips.Add (strip12);
		strips.Add (strip13);
		strips.Add (strip14);
		stripsCurrentIndex = 0;
		initialPositionY = this.transform.position.y;
	}
	
	// Update is called once per frame
	void Update () {
		if (!gamePlay) {
			return;
		}

		if (isDead) {
			return;
		}

		if (isJumpingUp) {
			if (this.transform.position.x > jumpTargetLocation.x) {
				this.transform.position = new Vector3 (this.transform.position.x - (movingSpeed * Time.deltaTime), this.transform.position.y, this.transform.position.z);
				if (this.transform.position.x > midPoint) {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y + jumpHeightIncrement * Time.deltaTime, this.transform.position.z);
				} else {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y - jumpHeightIncrement * Time.deltaTime, this.transform.position.z);

				}
			} else {
				isJumpingUp = false;
				this.transform.position = new Vector3 (this.transform.position.x, initialPositionY, this.transform.position.z);
			}
		} else if (isJumpingDown) {
			if (this.transform.position.x < jumpTargetLocation.x) {
				this.transform.position = new Vector3 (this.transform.position.x + (movingSpeed * Time.deltaTime), this.transform.position.y, this.transform.position.z);
				if (this.transform.position.x < midPoint) {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y + jumpHeightIncrement * Time.deltaTime, this.transform.position.z);
				} else {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y - jumpHeightIncrement * Time.deltaTime, this.transform.position.z);

				}
			} else {
				isJumpingDown = false;
				this.transform.position = new Vector3 (this.transform.position.x, initialPositionY, this.transform.position.z);
			}
		}
		else if (isJumpingLeft) {
			if (this.transform.position.z > jumpTargetLocation.z) {
				this.transform.position = new Vector3 (this.transform.position.x , this.transform.position.y, this.transform.position.z- (movingSpeed * Time.deltaTime));
				if (this.transform.position.z > midPoint) {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y + jumpHeightIncrement * Time.deltaTime, this.transform.position.z);
				} else {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y - jumpHeightIncrement * Time.deltaTime, this.transform.position.z);

				}
			} else {
				isJumpingLeft = false;
				this.transform.position = new Vector3 (this.transform.position.x, initialPositionY, this.transform.position.z);
			}
		}
		else if (isJumpingRight) {
			if (this.transform.position.z < jumpTargetLocation.z) {
				this.transform.position = new Vector3 (this.transform.position.x , this.transform.position.y, this.transform.position.z + (movingSpeed * Time.deltaTime));
				if (this.transform.position.z < midPoint) {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y + jumpHeightIncrement * Time.deltaTime, this.transform.position.z);
				} else {
					this.transform.position = new Vector3 (this.transform.position.x, this.transform.position.y - jumpHeightIncrement * Time.deltaTime, this.transform.position.z);

				}
			} else {
				isJumpingRight = false;
				this.transform.position = new Vector3 (this.transform.position.x, initialPositionY, this.transform.position.z);
			}
		}

		if (isPlayingDeadAnimation == true) {
			UpdateDeathAnimation ();
		}
	}

	void JumpUp(){
		//move strip index by one and the x there
		stripsCurrentIndex +=1;
		if (stripsCurrentIndex > highScore) {
			highScore = stripsCurrentIndex;
			scoreText.text = "Score:" + highScore.ToString ();
			Debug.Log ("" + highScore);
		}
		GameObject nextStrip = strips [stripsCurrentIndex] as GameObject;
		jumpTargetLocation = new Vector3 (nextStrip.transform.position.x-jumpOffsetX, transform.position.y, transform.position.z);
		midPoint = jumpTargetLocation.x + ((this.transform.position.x - jumpTargetLocation.x) / 2);

		//instantiane new strip
		mesh.transform.localEulerAngles = new Vector3 (0, 0, 0);

		createNewStrip ();
		float distanceX = this.transform.position.x - jumpTargetLocation.x;
		BoundaryLeft.transform.position -= new Vector3 (distanceX, 0, 0);
		BoundaryRight.transform.position -= new Vector3 (distanceX, 0, 0);

	}

	void createNewStrip(){
		//take a random strip from prefabs at random and put it at the end of the strip list
		int size = poolStripsPrefabs.Length;
		int rand = Random.Range (0, size);
		GameObject item = poolStripsPrefabs [rand] as GameObject;
		Transform itemChildTransform = item.transform.GetChild (0) as Transform;
		Transform itemChildofChildTransform = itemChildTransform.GetChild (0) as Transform;
		float itemWidth = itemChildofChildTransform.gameObject.GetComponent<Renderer> ().bounds.size.x;
		GameObject lastStrip = strips [strips.Count - 1] as GameObject;
		GameObject newStrip = Instantiate (item, lastStrip.transform.position, lastStrip.transform.rotation) as GameObject;
		newStrip.transform.position = new Vector3 (newStrip.transform.position.x - itemWidth, newStrip.transform.position.y, newStrip.transform.position.z);
		strips.Add (newStrip);
	}

	void OnTriggerEnter(Collider other){
		if (other.gameObject.tag == "enemy" || other.gameObject.tag == "truck") {
			Debug.Log("collision");
			DeathAnimation ();
		}
		if (other.gameObject.tag == "obstacle") {
			Debug.Log("hit obstacle");
			float offSetX = 0;
			float offSetZ = 0;
			if (isJumpingDown) {
				offSetX = -2.0f;
			}
			if (isJumpingUp) {
				offSetX = 2.0f;
			}
			if (isJumpingRight) {
				offSetZ = 2.0f;
			}
			if (isJumpingRight) {
				offSetZ = -2.0f;
			}
			transform.position = new Vector3 (transform.position.x+offSetX, initialPositionY, transform.position.z+offSetZ);
			isJumpingUp = isJumpingRight=isJumpingLeft=isJumpingDown=false;

		}
	}



	void DeathAnimation ()
	{
		isPlayingDeadAnimation = true;
		gameOverPanel.SetActive(true);

	}

	void UpdateDeathAnimation ()
	{
		if (mesh.transform.localScale.z > 0.1) {
			mesh.transform.localScale -= new Vector3(0,0,0.02f);
		} else {
			isPlayingDeadAnimation = false;
			isDead = true;
		}
		if (mesh.transform.rotation.eulerAngles.x == 0 || mesh.transform.rotation.eulerAngles.x > 270){
			mesh.transform.Rotate (-3.0f, 0, 0);
		}
	}

	void SwipeUp(){
		Debug.Log ("consuming swipe up");

			if (!isJumpingUp) {
				isJumpingUp = true;
				JumpUp ();
			}
	}

	void JumpDown ()
	{
		stripsCurrentIndex -=1;
		if (stripsCurrentIndex < 0) {
			stripsCurrentIndex = 0;
			return;
		}
		GameObject nextStrip = strips [stripsCurrentIndex] as GameObject;
		jumpTargetLocation = new Vector3 (nextStrip.transform.position.x-jumpOffsetX, transform.position.y, transform.position.z);
		midPoint = jumpTargetLocation.x - ((this.transform.position.x - jumpTargetLocation.x) / 2);
		mesh.transform.localEulerAngles = new Vector3 (0, 180, 0);
		float distanceX = this.transform.position.x - jumpTargetLocation.x;
		BoundaryLeft.transform.position += new Vector3 (distanceX, 0, 0);
		BoundaryRight.transform.position += new Vector3 (distanceX, 0, 0);
	}

	void SwipeDown(){
		Debug.Log ("consuming swipe down");

			if (!isJumpingDown) {
				isJumpingDown = true;
				JumpDown ();
			}

	}

	void JumpRight ()	
	{
		jumpTargetLocation = new Vector3 (transform.position.x, transform.position.y, transform.position.z + jumpDistanceZ);
		midPoint = jumpTargetLocation.z + ((this.transform.position.z-jumpTargetLocation.z ) / 2);
		mesh.transform.localEulerAngles = new Vector3(0,90,0);
	}

	void SwipeRight(){
		Debug.Log ("consuming swipe right");

			if (!isJumpingRight) {
				isJumpingRight= true;
				JumpRight ();
			}

	}

	void JumpLeft ()
	{
		jumpTargetLocation = new Vector3 (transform.position.x, transform.position.y, transform.position.z - jumpDistanceZ);
		midPoint = jumpTargetLocation.z - ((jumpTargetLocation.z-this.transform.position.z ) / 2);
		mesh.transform.localEulerAngles = new Vector3(0,-90,0);


	}

	void SwipeLeft(){
		Debug.Log ("consuming swipe left");
		if (!isJumpingLeft) {
			isJumpingLeft= true;
			JumpLeft ();
		}

	}

	void ButtonStartPressed(){
		gamePlay = true;
		startPanel.SetActive(false);
	}
}
