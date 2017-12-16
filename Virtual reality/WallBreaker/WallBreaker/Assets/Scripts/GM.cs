using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class GM : MonoBehaviour {
	public int lives = 3;
	public int bricks = 20;
	public float resetDelay = 1f;
	public Text livesText;
	public GameObject gameOver;
	public GameObject youWon;
	public GameObject bricksPrefab;
	public GameObject paddle;
	public GameObject deathParticles;
	//singleton pattern in the game manager 
	public static GM instance = null;
	private GameObject clonePaddle;

	// Use this for initialization
	void Awake () {
		if (instance == null)
			instance = this;
		else if (instance != this)
			Destroy (gameObject);
		//prevent second manager on potential level 2 
		Setup();
	}

	public void Setup(){
		//quaternion identity--> no rotation
		clonePaddle = Instantiate (paddle, transform.position, Quaternion.identity) as GameObject;
		Instantiate (bricksPrefab, transform.position, Quaternion.identity);
	}

	public void checkGameOver()
	{
		if (bricks < 1) {
			youWon.SetActive (true);
			Time.timeScale = 0.25f;
			Invoke ("Reset", resetDelay);
		}
		if (lives < 1) {
			gameOver.SetActive (true);
			Time.timeScale = 0.25f;
			Invoke ("Reset", resetDelay);
		}
	}

	public void Reset(){
		//no more slow motion
		Time.timeScale = 1f;
		Application.LoadLevel (Application.loadedLevel);
	}
	
	public void LoseLife(){
		lives--;
		livesText.text = "Lives: " + lives.ToString();
		Instantiate (deathParticles, clonePaddle.transform.position,Quaternion.identity);
		Destroy (clonePaddle);
		Invoke ("SetupPaddle", resetDelay);
		checkGameOver ();
	}

	private void SetupPaddle(){
		clonePaddle = Instantiate (paddle, transform.position, Quaternion.identity) as GameObject;
	}

	public void destroyBrick(){
		bricks--;
		checkGameOver();
	}
}
