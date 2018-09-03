import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';

@Component({
    selector: 'jhi-organization-pulse-detail',
    templateUrl: './organization-pulse-detail.component.html'
})
export class OrganizationPulseDetailComponent implements OnInit {
    organization: IOrganizationPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ organization }) => {
            this.organization = organization;
        });
    }

    previousState() {
        window.history.back();
    }
}
