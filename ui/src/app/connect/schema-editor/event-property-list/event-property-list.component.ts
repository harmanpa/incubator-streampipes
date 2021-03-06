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

import { Component, DoCheck, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EventPropertyList } from '../model/EventPropertyList';
import { EventProperty } from '../model/EventProperty';
import { DataTypesService } from '../data-type.service';


@Component({
  selector: 'app-event-property-list',
  templateUrl: './event-property-list.component.html',
  styleUrls: ['./event-property-list.component.css']
})
export class EventPropertyListComponent implements OnInit {

  constructor(private dataTypeService: DataTypesService) { }


  @Input() property: EventPropertyList;
  @Input() index: number;

  private runtimeDataTypes;

  @Input() isEditable: boolean;

  @Output() delete: EventEmitter<EventProperty> = new EventEmitter<EventProperty>();

  ngOnInit() {
    this.runtimeDataTypes = this.dataTypeService.getDataTypes();
  }
}
