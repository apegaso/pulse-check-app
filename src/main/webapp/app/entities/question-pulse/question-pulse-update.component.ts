import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from './question-pulse.service';
import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';
import { QuestionGroupPulseService } from 'app/entities/question-group-pulse';
import { ICategoryPulse } from 'app/shared/model/category-pulse.model';
import { CategoryPulseService } from 'app/entities/category-pulse';

@Component({
    selector: 'jhi-question-pulse-update',
    templateUrl: './question-pulse-update.component.html'
})
export class QuestionPulseUpdateComponent implements OnInit {
    private _question: IQuestionPulse;
    isSaving: boolean;

    questiongroups: IQuestionGroupPulse[];

    categories: ICategoryPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionPulseService,
        private questionGroupService: QuestionGroupPulseService,
        private categoryService: CategoryPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
        this.questionGroupService.query().subscribe(
            (res: HttpResponse<IQuestionGroupPulse[]>) => {
                this.questiongroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategoryPulse[]>) => {
                this.categories = res.body;
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

    trackQuestionGroupById(index: number, item: IQuestionGroupPulse) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategoryPulse) {
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
    get question() {
        return this._question;
    }

    set question(question: IQuestionPulse) {
        this._question = question;
    }
}
