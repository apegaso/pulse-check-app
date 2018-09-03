import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';
import { OrganizationPulseService } from './organization-pulse.service';
import { IEventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from 'app/entities/event-pulse';
import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';
import { OrgAdminPulseService } from 'app/entities/org-admin-pulse';

@Component({
    selector: 'jhi-organization-pulse-update',
    templateUrl: './organization-pulse-update.component.html'
})
export class OrganizationPulseUpdateComponent implements OnInit {
    private _organization: IOrganizationPulse;
    isSaving: boolean;

    events: IEventPulse[];

    orgadmins: IOrgAdminPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private organizationService: OrganizationPulseService,
        private eventService: EventPulseService,
        private orgAdminService: OrgAdminPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ organization }) => {
            this.organization = organization;
        });
        this.eventService.query().subscribe(
            (res: HttpResponse<IEventPulse[]>) => {
                this.events = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orgAdminService.query().subscribe(
            (res: HttpResponse<IOrgAdminPulse[]>) => {
                this.orgadmins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.organization.id !== undefined) {
            this.subscribeToSaveResponse(this.organizationService.update(this.organization));
        } else {
            this.subscribeToSaveResponse(this.organizationService.create(this.organization));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationPulse>>) {
        result.subscribe((res: HttpResponse<IOrganizationPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEventById(index: number, item: IEventPulse) {
        return item.id;
    }

    trackOrgAdminById(index: number, item: IOrgAdminPulse) {
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
    get organization() {
        return this._organization;
    }

    set organization(organization: IOrganizationPulse) {
        this._organization = organization;
    }
}
