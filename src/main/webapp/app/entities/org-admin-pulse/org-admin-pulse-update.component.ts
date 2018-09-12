import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';
import { OrgAdminPulseService } from './org-admin-pulse.service';
import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';
import { UserExtPulseService } from 'app/entities/user-ext-pulse';
import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';
import { OrganizationPulseService } from 'app/entities/organization-pulse';

@Component({
    selector: 'jhi-org-admin-pulse-update',
    templateUrl: './org-admin-pulse-update.component.html'
})
export class OrgAdminPulseUpdateComponent implements OnInit {
    private _orgAdmin: IOrgAdminPulse;
    isSaving: boolean;

    userexts: IUserExtPulse[];

    organizations: IOrganizationPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orgAdminService: OrgAdminPulseService,
        private userExtService: UserExtPulseService,
        private organizationService: OrganizationPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orgAdmin }) => {
            this.orgAdmin = orgAdmin;
        });
        this.userExtService.query({ filter: 'orgadmin-is-null' }).subscribe(
            (res: HttpResponse<IUserExtPulse[]>) => {
                if (!this.orgAdmin.userExtId) {
                    this.userexts = res.body;
                } else {
                    this.userExtService.find(this.orgAdmin.userExtId).subscribe(
                        (subRes: HttpResponse<IUserExtPulse>) => {
                            this.userexts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.organizationService.query().subscribe(
            (res: HttpResponse<IOrganizationPulse[]>) => {
                this.organizations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orgAdmin.id !== undefined) {
            this.subscribeToSaveResponse(this.orgAdminService.update(this.orgAdmin));
        } else {
            this.subscribeToSaveResponse(this.orgAdminService.create(this.orgAdmin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrgAdminPulse>>) {
        result.subscribe((res: HttpResponse<IOrgAdminPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserExtById(index: number, item: IUserExtPulse) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganizationPulse) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get orgAdmin() {
        return this._orgAdmin;
    }

    set orgAdmin(orgAdmin: IOrgAdminPulse) {
        this._orgAdmin = orgAdmin;
    }
}
