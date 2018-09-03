import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';

@Component({
    selector: 'jhi-questionnaire-answer-pulse-detail',
    templateUrl: './questionnaire-answer-pulse-detail.component.html'
})
export class QuestionnaireAnswerPulseDetailComponent implements OnInit {
    questionnaireAnswer: IQuestionnaireAnswerPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionnaireAnswer }) => {
            this.questionnaireAnswer = questionnaireAnswer;
        });
    }

    previousState() {
        window.history.back();
    }
}
