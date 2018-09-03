import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';

@Component({
    selector: 'jhi-org-admin-pulse-detail',
    templateUrl: './org-admin-pulse-detail.component.html'
})
export class OrgAdminPulseDetailComponent implements OnInit {
    orgAdmin: IOrgAdminPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orgAdmin }) => {
            this.orgAdmin = orgAdmin;
        });
    }

    previousState() {
        window.history.back();
    }
}
