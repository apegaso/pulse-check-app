import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';

@Component({
    selector: 'jhi-client-lead-pulse-detail',
    templateUrl: './client-lead-pulse-detail.component.html'
})
export class ClientLeadPulseDetailComponent implements OnInit {
    clientLead: IClientLeadPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientLead }) => {
            this.clientLead = clientLead;
        });
    }

    previousState() {
        window.history.back();
    }
}
