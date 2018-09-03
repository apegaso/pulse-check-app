import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from './participant-pulse.service';
import { IEventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from 'app/entities/event-pulse';

@Component({
    selector: 'jhi-participant-pulse-update',
    templateUrl: './participant-pulse-update.component.html'
})
export class ParticipantPulseUpdateComponent implements OnInit {
    private _participant: IParticipantPulse;
    isSaving: boolean;

    events: IEventPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private participantService: ParticipantPulseService,
        private eventService: EventPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ participant }) => {
            this.participant = participant;
        });
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
        if (this.participant.id !== undefined) {
            this.subscribeToSaveResponse(this.participantService.update(this.participant));
        } else {
            this.subscribeToSaveResponse(this.participantService.create(this.participant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParticipantPulse>>) {
        result.subscribe((res: HttpResponse<IParticipantPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get participant() {
        return this._participant;
    }

    set participant(participant: IParticipantPulse) {
        this._participant = participant;
    }
}
