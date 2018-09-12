import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';
import { UserExtPulseService } from './user-ext-pulse.service';
import { IUser, UserService } from 'app/core';
import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';
import { OrganizationPulseService } from 'app/entities/organization-pulse';
import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';
import { ClientLeadPulseService } from 'app/entities/client-lead-pulse';
import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';
import { OrgAdminPulseService } from 'app/entities/org-admin-pulse';
import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from 'app/entities/participant-pulse';

@Component({
    selector: 'jhi-user-ext-pulse-update',
    templateUrl: './user-ext-pulse-update.component.html'
})
export class UserExtPulseUpdateComponent implements OnInit {
    private _userExt: IUserExtPulse;
    isSaving: boolean;

    users: IUser[];

    organizations: IOrganizationPulse[];

    clientleads: IClientLeadPulse[];

    orgadmins: IOrgAdminPulse[];

    participants: IParticipantPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private userExtService: UserExtPulseService,
        private userService: UserService,
        private organizationService: OrganizationPulseService,
        private clientLeadService: ClientLeadPulseService,
        private orgAdminService: OrgAdminPulseService,
        private participantService: ParticipantPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userExt }) => {
            this.userExt = userExt;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.organizationService.query().subscribe(
            (res: HttpResponse<IOrganizationPulse[]>) => {
                this.organizations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clientLeadService.query().subscribe(
            (res: HttpResponse<IClientLeadPulse[]>) => {
                this.clientleads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orgAdminService.query().subscribe(
            (res: HttpResponse<IOrgAdminPulse[]>) => {
                this.orgadmins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.participantService.query().subscribe(
            (res: HttpResponse<IParticipantPulse[]>) => {
                this.participants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userExt.id !== undefined) {
            this.subscribeToSaveResponse(this.userExtService.update(this.userExt));
        } else {
            this.subscribeToSaveResponse(this.userExtService.create(this.userExt));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtPulse>>) {
        result.subscribe((res: HttpResponse<IUserExtPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganizationPulse) {
        return item.id;
    }

    trackClientLeadById(index: number, item: IClientLeadPulse) {
        return item.id;
    }

    trackOrgAdminById(index: number, item: IOrgAdminPulse) {
        return item.id;
    }

    trackParticipantById(index: number, item: IParticipantPulse) {
        return item.id;
    }
    get userExt() {
        return this._userExt;
    }

    set userExt(userExt: IUserExtPulse) {
        this._userExt = userExt;
    }
}
