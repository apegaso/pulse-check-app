import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from './question-pulse.service';
import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';
import { QuestionCategoryPulseService } from 'app/entities/question-category-pulse';

@Component({
    selector: 'jhi-question-pulse-update',
    templateUrl: './question-pulse-update.component.html'
})
export class QuestionPulseUpdateComponent implements OnInit {
    private _question: IQuestionPulse;
    isSaving: boolean;

    questioncategories: IQuestionCategoryPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionPulseService,
        private questionCategoryService: QuestionCategoryPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
        this.questionCategoryService.query().subscribe(
            (res: HttpResponse<IQuestionCategoryPulse[]>) => {
                this.questioncategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionPulse>>) {
        result.subscribe((res: HttpResponse<IQuestionPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get question() {
        return this._question;
    }

    set question(question: IQuestionPulse) {
        this._question = question;
    }
}
