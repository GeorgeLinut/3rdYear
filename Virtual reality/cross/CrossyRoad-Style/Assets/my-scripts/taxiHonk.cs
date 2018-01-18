using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class taxiHonk : MonoBehaviour {
	public AudioClip saw;
	public AudioSource src;
	public AudioClip saw1;
	public AudioSource src1;

	void Start ()
	{
		src.clip = saw;
		src1.clip = saw1;
	}

	void OnTriggerEnter(Collider other){
		if (other.gameObject.tag == "enemy") {
			src.Play ();
			Debug.Log("taxi mama lui");
		}
	}
	void OnTriggerExit(Collider other){
		if (other.gameObject.tag == "enemy") {
			src1.Play ();
			Debug.Log("blana taxi");
		}
	}

}
