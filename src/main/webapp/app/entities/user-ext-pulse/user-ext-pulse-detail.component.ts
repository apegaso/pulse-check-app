import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';

@Component({
    selector: 'jhi-user-ext-pulse-detail',
    templateUrl: './user-ext-pulse-detail.component.html'
})
export class UserExtPulseDetailComponent implements OnInit {
    userExt: IUserExtPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userExt }) => {
            this.userExt = userExt;
        });
    }

    previousState() {
        window.history.back();
    }
}
