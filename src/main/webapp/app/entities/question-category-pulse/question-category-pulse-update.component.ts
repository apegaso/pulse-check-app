import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';
import { QuestionCategoryPulseService } from './question-category-pulse.service';
import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from 'app/entities/question-pulse';

@Component({
    selector: 'jhi-question-category-pulse-update',
    templateUrl: './question-category-pulse-update.component.html'
})
export class QuestionCategoryPulseUpdateComponent implements OnInit {
    private _questionCategory: IQuestionCategoryPulse;
    isSaving: boolean;

    questioncategories: IQuestionCategoryPulse[];

    questions: IQuestionPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionCategoryService: QuestionCategoryPulseService,
        private questionService: QuestionPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionCategory }) => {
            this.questionCategory = questionCategory;
        });
        this.questionCategoryService.query().subscribe(
            (res: HttpResponse<IQuestionCategoryPulse[]>) => {
                this.questioncategories = res.body;
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
        if (this.questionCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.questionCategoryService.update(this.questionCategory));
        } else {
            this.subscribeToSaveResponse(this.questionCategoryService.create(this.questionCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionCategoryPulse>>) {
        result.subscribe(
            (res: HttpResponse<IQuestionCategoryPulse>) => this.onSaveSuccess(),
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

    trackQuestionCategoryById(index: number, item: IQuestionCategoryPulse) {
        return item.id;
    }

    trackQuestionById(index: number, item: IQuestionPulse) {
        return item.id;
    }
    get questionCategory() {
        return this._questionCategory;
    }

    set questionCategory(questionCategory: IQuestionCategoryPulse) {
        this._questionCategory = questionCategory;
    }
}
