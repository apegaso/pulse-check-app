import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';
import { QuestionCategoryPulseService } from './question-category-pulse.service';
import { QuestionCategoryPulseComponent } from './question-category-pulse.component';
import { QuestionCategoryPulseDetailComponent } from './question-category-pulse-detail.component';
import { QuestionCategoryPulseUpdateComponent } from './question-category-pulse-update.component';
import { QuestionCategoryPulseDeletePopupComponent } from './question-category-pulse-delete-dialog.component';
import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

@Injectable({ providedIn: 'root' })
export class QuestionCategoryPulseResolve implements Resolve<IQuestionCategoryPulse> {
    constructor(private service: QuestionCategoryPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((questionCategory: HttpResponse<QuestionCategoryPulse>) => questionCategory.body));
        }
        return of(new QuestionCategoryPulse());
    }
}

export const questionCategoryRoute: Routes = [
    {
        path: 'question-category-pulse',
        component: QuestionCategoryPulseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-category-pulse/:id/view',
        component: QuestionCategoryPulseDetailComponent,
        resolve: {
            questionCategory: QuestionCategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-category-pulse/new',
        component: QuestionCategoryPulseUpdateComponent,
        resolve: {
            questionCategory: QuestionCategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-category-pulse/:id/edit',
        component: QuestionCategoryPulseUpdateComponent,
        resolve: {
            questionCategory: QuestionCategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionCategoryPopupRoute: Routes = [
    {
        path: 'question-category-pulse/:id/delete',
        component: QuestionCategoryPulseDeletePopupComponent,
        resolve: {
            questionCategory: QuestionCategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
