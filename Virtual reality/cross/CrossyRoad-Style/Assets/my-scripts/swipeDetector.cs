using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class swipeDetector : MonoBehaviour {

	private const int mMessageWidth = 200;
	private const int mMessageHeight = 64;

	private readonly Vector2 mXAxis = new Vector2 (1, 0);
	private readonly Vector2 mYAxis = new Vector2(0,1);

	private const float mAngleRange = 30;
	private const float mMinSwipeDist = 50.0f;
	private const float mMinVelocity = 400.0f;
	private Vector2 mStartPosition;
	private float mSwipeStartTime;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		if (Input.GetMouseButtonDown (0)) {
			mStartPosition = new Vector2 (Input.mousePosition.x, Input.mousePosition.y);
			mSwipeStartTime = Time.time;
		}
		if (Input.GetMouseButtonUp(0)){
			float deltaTime = Time.time - mSwipeStartTime;
			Vector2 endPosition = new Vector2 (Input.mousePosition.x,Input.mousePosition.y);
			Vector2 swipeVector = endPosition - mStartPosition;
			float velocity = swipeVector.magnitude / deltaTime;

			if (velocity > mMinVelocity && swipeVector.magnitude > mMinSwipeDist) {
				swipeVector.Normalize ();
				float angleOfSwipe = Vector2.Dot (swipeVector, mXAxis);
				angleOfSwipe = Mathf.Acos (angleOfSwipe) * Mathf.Rad2Deg;

				if (angleOfSwipe < mAngleRange) {
					OnSwipeRight ();
				} else if ((180.0f - angleOfSwipe) < mAngleRange) {
					OnSwipeLeft ();
				} else {
					angleOfSwipe = Vector2.Dot (swipeVector, mYAxis);
					angleOfSwipe = Mathf.Acos (angleOfSwipe) * Mathf.Rad2Deg;
					if (angleOfSwipe < mAngleRange) {
						OnSwipeTop ();
					} else if ((180.0f - angleOfSwipe) < mAngleRange) {
						OnSwipeBottom ();
					} else {
						//no swipe
					}
				}
			}
	}
}

	private void OnSwipeLeft(){
		Debug.Log ("Swipe left");
		BroadcastMessage ("SwipeLeft");
	}
	private void OnSwipeRight(){
		Debug.Log ("Swipe right");
		BroadcastMessage ("SwipeRight");
	}
	private void OnSwipeTop(){
		Debug.Log ("Swipe up");
		BroadcastMessage ("SwipeUp");
	}
	private void OnSwipeBottom(){
		Debug.Log ("Swipe down");
		BroadcastMessage ("SwipeDown");
	}
}
