import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionPulse } from 'app/shared/model/question-pulse.model';

@Component({
    selector: 'jhi-question-pulse-detail',
    templateUrl: './question-pulse-detail.component.html'
})
export class QuestionPulseDetailComponent implements OnInit {
    question: IQuestionPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
    }

    previousState() {
        window.history.back();
    }
}
