import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';
import { CategoryLevelPulseService } from './category-level-pulse.service';

@Component({
    selector: 'jhi-category-level-pulse-update',
    templateUrl: './category-level-pulse-update.component.html'
})
export class CategoryLevelPulseUpdateComponent implements OnInit {
    private _categoryLevel: ICategoryLevelPulse;
    isSaving: boolean;

    constructor(private categoryLevelService: CategoryLevelPulseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoryLevel }) => {
            this.categoryLevel = categoryLevel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categoryLevel.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryLevelService.update(this.categoryLevel));
        } else {
            this.subscribeToSaveResponse(this.categoryLevelService.create(this.categoryLevel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryLevelPulse>>) {
        result.subscribe((res: HttpResponse<ICategoryLevelPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get categoryLevel() {
        return this._categoryLevel;
    }

    set categoryLevel(categoryLevel: ICategoryLevelPulse) {
        this._categoryLevel = categoryLevel;
    }
}
