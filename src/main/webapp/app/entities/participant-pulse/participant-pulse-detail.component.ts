import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';

@Component({
    selector: 'jhi-participant-pulse-detail',
    templateUrl: './participant-pulse-detail.component.html'
})
export class ParticipantPulseDetailComponent implements OnInit {
    participant: IParticipantPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ participant }) => {
            this.participant = participant;
        });
    }

    previousState() {
        window.history.back();
    }
}
