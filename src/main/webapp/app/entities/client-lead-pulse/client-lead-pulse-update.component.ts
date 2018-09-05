import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';
import { ClientLeadPulseService } from './client-lead-pulse.service';
import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';
import { UserExtPulseService } from 'app/entities/user-ext-pulse';
import { IEventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from 'app/entities/event-pulse';

@Component({
    selector: 'jhi-client-lead-pulse-update',
    templateUrl: './client-lead-pulse-update.component.html'
})
export class ClientLeadPulseUpdateComponent implements OnInit {
    private _clientLead: IClientLeadPulse;
    isSaving: boolean;

    userexts: IUserExtPulse[];

    events: IEventPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private clientLeadService: ClientLeadPulseService,
        private userExtService: UserExtPulseService,
        private eventService: EventPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clientLead }) => {
            this.clientLead = clientLead;
        });
        this.userExtService.query({ filter: 'clientlead-is-null' }).subscribe(
            (res: HttpResponse<IUserExtPulse[]>) => {
                if (!this.clientLead.userExtId) {
                    this.userexts = res.body;
                } else {
                    this.userExtService.find(this.clientLead.userExtId).subscribe(
                        (subRes: HttpResponse<IUserExtPulse>) => {
                            this.userexts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.eventService.query().subscribe(
            (res: HttpResponse<IEventPulse[]>) => {
                this.events = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clientLead.id !== undefined) {
            this.subscribeToSaveResponse(this.clientLeadService.update(this.clientLead));
        } else {
            this.subscribeToSaveResponse(this.clientLeadService.create(this.clientLead));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClientLeadPulse>>) {
        result.subscribe((res: HttpResponse<IClientLeadPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEventById(index: number, item: IEventPulse) {
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
    get clientLead() {
        return this._clientLead;
    }

    set clientLead(clientLead: IClientLeadPulse) {
        this._clientLead = clientLead;
    }
}
