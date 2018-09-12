import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryPulse } from 'app/shared/model/category-pulse.model';

@Component({
    selector: 'jhi-category-pulse-detail',
    templateUrl: './category-pulse-detail.component.html'
})
export class CategoryPulseDetailComponent implements OnInit {
    category: ICategoryPulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
        });
    }

    previousState() {
        window.history.back();
    }
}
