using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DeathZone : MonoBehaviour {

	void OnTriggerEnter(Collider col){
		GM.instance.LoseLife();
	}
}
