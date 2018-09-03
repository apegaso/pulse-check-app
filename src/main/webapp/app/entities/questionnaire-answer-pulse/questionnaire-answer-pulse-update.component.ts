import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';
import { QuestionnaireAnswerPulseService } from './questionnaire-answer-pulse.service';
import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';
import { QuestionnairePulseService } from 'app/entities/questionnaire-pulse';
import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from 'app/entities/question-pulse';

@Component({
    selector: 'jhi-questionnaire-answer-pulse-update',
    templateUrl: './questionnaire-answer-pulse-update.component.html'
})
export class QuestionnaireAnswerPulseUpdateComponent implements OnInit {
    private _questionnaireAnswer: IQuestionnaireAnswerPulse;
    isSaving: boolean;

    questionnaires: IQuestionnairePulse[];

    questions: IQuestionPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionnaireAnswerService: QuestionnaireAnswerPulseService,
        private questionnaireService: QuestionnairePulseService,
        private questionService: QuestionPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionnaireAnswer }) => {
            this.questionnaireAnswer = questionnaireAnswer;
        });
        this.questionnaireService.query().subscribe(
            (res: HttpResponse<IQuestionnairePulse[]>) => {
                this.questionnaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.questionService.query().subscribe(
            (res: HttpResponse<IQuestionPulse[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.questionnaireAnswer.id !== undefined) {
            this.subscribeToSaveResponse(this.questionnaireAnswerService.update(this.questionnaireAnswer));
        } else {
            this.subscribeToSaveResponse(this.questionnaireAnswerService.create(this.questionnaireAnswer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnaireAnswerPulse>>) {
        result.subscribe(
            (res: HttpResponse<IQuestionnaireAnswerPulse>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackQuestionnaireById(index: number, item: IQuestionnairePulse) {
        return item.id;
    }

    trackQuestionById(index: number, item: IQuestionPulse) {
        return item.id;
    }
    get questionnaireAnswer() {
        return this._questionnaireAnswer;
    }

    set questionnaireAnswer(questionnaireAnswer: IQuestionnaireAnswerPulse) {
        this._questionnaireAnswer = questionnaireAnswer;
    }
}
