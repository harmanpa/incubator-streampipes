/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StaticProperty } from '../../model/StaticProperty';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { ValidateString } from '../../select-protocol-component/input.validator';
import {StaticPropertyUtilService} from '../static-property-util.service';
import {ConfigurationInfo} from "../../model/message/ConfigurationInfo";

@Component({
    selector: 'app-static-text-input',
    templateUrl: './static-text-input.component.html',
    styleUrls: ['./static-text-input.component.css']
})
export class StaticTextInputComponent implements OnInit {

    constructor(private staticPropertyUtil: StaticPropertyUtilService){

    }

    @Output() updateEmitter: EventEmitter<ConfigurationInfo> = new EventEmitter();

    @Input() staticProperty: StaticProperty;
    @Output() inputEmitter: EventEmitter<any> = new EventEmitter<any>();

    private freeTextForm: FormGroup;
    private inputValue: String;
    private hasInput: Boolean;
    private errorMessage = "Please enter a valid Text";
    ngOnInit() {
        this.freeTextForm = new FormGroup({
            'freeStaticTextString': new FormControl(this.inputValue, [
                Validators.required,
                ValidateString
            ]),
        })
    }
    
    valueChange(inputValue) {
        this.inputValue = inputValue;

        if (inputValue == "" || !inputValue) {
            this.hasInput = false;
        }
        //STRING VALIDATOR      
        else if (inputValue.length > 5 && inputValue.includes("@")) {
            this.hasInput = true;
        }

        else {
            this.hasInput = false;
        }

        this.inputEmitter.emit(this.hasInput);

    }

    emitUpdate() {
        this.updateEmitter.emit(new ConfigurationInfo(this.staticProperty.internalName, this.staticPropertyUtil.asFreeTextStaticProperty(this.staticProperty).value && this.staticPropertyUtil.asFreeTextStaticProperty(this.staticProperty).value !== ""));
    }

}