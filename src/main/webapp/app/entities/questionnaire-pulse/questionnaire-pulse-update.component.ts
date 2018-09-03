import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';
import { QuestionnairePulseService } from './questionnaire-pulse.service';
import { IEventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from 'app/entities/event-pulse';
import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from 'app/entities/participant-pulse';

@Component({
    selector: 'jhi-questionnaire-pulse-update',
    templateUrl: './questionnaire-pulse-update.component.html'
})
export class QuestionnairePulseUpdateComponent implements OnInit {
    private _questionnaire: IQuestionnairePulse;
    isSaving: boolean;

    events: IEventPulse[];

    participants: IParticipantPulse[];
    dateStart: string;
    dateEnd: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionnaireService: QuestionnairePulseService,
        private eventService: EventPulseService,
        private participantService: ParticipantPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionnaire }) => {
            this.questionnaire = questionnaire;
        });
        this.eventService.query().subscribe(
            (res: HttpResponse<IEventPulse[]>) => {
                this.events = res.body;
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
        this.questionnaire.dateStart = moment(this.dateStart, DATE_TIME_FORMAT);
        this.questionnaire.dateEnd = moment(this.dateEnd, DATE_TIME_FORMAT);
        if (this.questionnaire.id !== undefined) {
            this.subscribeToSaveResponse(this.questionnaireService.update(this.questionnaire));
        } else {
            this.subscribeToSaveResponse(this.questionnaireService.create(this.questionnaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnairePulse>>) {
        result.subscribe((res: HttpResponse<IQuestionnairePulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackParticipantById(index: number, item: IParticipantPulse) {
        return item.id;
    }
    get questionnaire() {
        return this._questionnaire;
    }

    set questionnaire(questionnaire: IQuestionnairePulse) {
        this._questionnaire = questionnaire;
        this.dateStart = moment(questionnaire.dateStart).format(DATE_TIME_FORMAT);
        this.dateEnd = moment(questionnaire.dateEnd).format(DATE_TIME_FORMAT);
    }
}
