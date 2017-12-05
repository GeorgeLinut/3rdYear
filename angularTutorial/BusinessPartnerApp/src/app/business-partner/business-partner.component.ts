import { Component, OnInit } from '@angular/core';
import {BusinessPartner} from "../model/businesspartner";

@Component({
  selector: 'app-business-partner',
  templateUrl: './business-partner.component.html',
  styleUrls: ['./business-partner.component.css']
})
export class BusinessPartnerComponent implements OnInit {

  BusinessPartnerList: BusinessPartner[];

  constructor() { }

  ngOnInit() {
    this.BusinessPartnerList =[]
    this.BusinessPartnerList.push(({firstName:"John",surName:"John2",gender:"M",age:12,phoneNr:12}))
  }

  onItemClicked(businessPartnerName:string){
    console.log(businessPartnerName);
  }

}
