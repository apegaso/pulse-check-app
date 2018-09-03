import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

@Component({
    selector: 'jhi-question-category-pulse-detail',
    templateUrl: './question-category-pulse-detail.component.html'
})
export class QuestionCategoryPulseDetailComponent implements OnInit {
    questionCategory: IQuestionCategoryPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionCategory }) => {
            this.questionCategory = questionCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
