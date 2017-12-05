import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BusinessPartner} from "../../model/businesspartner";

@Component({
  selector: 'app-business-partner-item',
  templateUrl: './business-partner-item.component.html',
  styleUrls: ['./business-partner-item.component.css']
})
export class BusinessPartnerItemComponent implements OnInit {
  @Input()   businessPartner:BusinessPartner;

  @Output() businessPartnerClicked = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  onClick():void{
    this.businessPartnerClicked.emit(this.businessPartner.firstName);
  }

}
