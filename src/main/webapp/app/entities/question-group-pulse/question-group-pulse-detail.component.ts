import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

@Component({
    selector: 'jhi-question-group-pulse-detail',
    templateUrl: './question-group-pulse-detail.component.html'
})
export class QuestionGroupPulseDetailComponent implements OnInit {
    questionGroup: IQuestionGroupPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionGroup }) => {
            this.questionGroup = questionGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
