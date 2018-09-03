import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventPulse } from 'app/shared/model/event-pulse.model';

@Component({
    selector: 'jhi-event-pulse-detail',
    templateUrl: './event-pulse-detail.component.html'
})
export class EventPulseDetailComponent implements OnInit {
    event: IEventPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ event }) => {
            this.event = event;
        });
    }

    previousState() {
        window.history.back();
    }
}
