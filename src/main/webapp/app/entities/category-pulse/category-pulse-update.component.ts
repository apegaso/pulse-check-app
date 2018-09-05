import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICategoryPulse } from 'app/shared/model/category-pulse.model';
import { CategoryPulseService } from './category-pulse.service';
import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';
import { CategoryLevelPulseService } from 'app/entities/category-level-pulse';
import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from 'app/entities/question-pulse';

@Component({
    selector: 'jhi-category-pulse-update',
    templateUrl: './category-pulse-update.component.html'
})
export class CategoryPulseUpdateComponent implements OnInit {
    private _category: ICategoryPulse;
    isSaving: boolean;

    categories: ICategoryPulse[];

    categorylevels: ICategoryLevelPulse[];

    questions: IQuestionPulse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private categoryService: CategoryPulseService,
        private categoryLevelService: CategoryLevelPulseService,
        private questionService: QuestionPulseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
        });
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategoryPulse[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryLevelService.query().subscribe(
            (res: HttpResponse<ICategoryLevelPulse[]>) => {
                this.categorylevels = res.body;
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
        if (this.category.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryService.update(this.category));
        } else {
            this.subscribeToSaveResponse(this.categoryService.create(this.category));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryPulse>>) {
        result.subscribe((res: HttpResponse<ICategoryPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategoryPulse) {
        return item.id;
    }

    trackCategoryLevelById(index: number, item: ICategoryLevelPulse) {
        return item.id;
    }

    trackQuestionById(index: number, item: IQuestionPulse) {
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
    get category() {
        return this._category;
    }

    set category(category: ICategoryPulse) {
        this._category = category;
    }
}
