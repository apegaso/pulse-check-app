import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';

@Component({
    selector: 'jhi-category-level-pulse-detail',
    templateUrl: './category-level-pulse-detail.component.html'
})
export class CategoryLevelPulseDetailComponent implements OnInit {
    categoryLevel: ICategoryLevelPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoryLevel }) => {
            this.categoryLevel = categoryLevel;
        });
    }

    previousState() {
        window.history.back();
    }
}
