import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from './event-pulse.service';
import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from 'app/entities/participant-pulse';
import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';
import { ClientLeadPulseService } from 'app/entities/client-lead-pulse';

@Component({
    selector: 'jhi-event-pulse-update',
    templateUrl: './event-pulse-update.component.html'
})
export class EventPulseUpdateComponent implements OnInit {
    private _event: IEventPulse;
    isSaving: boolean;

    participants: IParticipantPulse[];

    clientleads: IClientLeadPulse[];
    eventDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventService: EventPulseService,
        private participantService: ParticipantPulseService,
        private clientLeadService: ClientLeadPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ event }) => {
            this.event = event;
        });
        this.participantService.query().subscribe(
            (res: HttpResponse<IParticipantPulse[]>) => {
                this.participants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clientLeadService.query().subscribe(
            (res: HttpResponse<IClientLeadPulse[]>) => {
                this.clientleads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.event.eventDate = moment(this.eventDate, DATE_TIME_FORMAT);
        if (this.event.id !== undefined) {
            this.subscribeToSaveResponse(this.eventService.update(this.event));
        } else {
            this.subscribeToSaveResponse(this.eventService.create(this.event));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEventPulse>>) {
        result.subscribe((res: HttpResponse<IEventPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackParticipantById(index: number, item: IParticipantPulse) {
        return item.id;
    }

    trackClientLeadById(index: number, item: IClientLeadPulse) {
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
    get event() {
        return this._event;
    }

    set event(event: IEventPulse) {
        this._event = event;
        this.eventDate = moment(event.eventDate).format(DATE_TIME_FORMAT);
    }
}
