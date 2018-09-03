import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    QuestionCategoryPulseComponent,
    QuestionCategoryPulseDetailComponent,
    QuestionCategoryPulseUpdateComponent,
    QuestionCategoryPulseDeletePopupComponent,
    QuestionCategoryPulseDeleteDialogComponent,
    questionCategoryRoute,
    questionCategoryPopupRoute
} from './';

const ENTITY_STATES = [...questionCategoryRoute, ...questionCategoryPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionCategoryPulseComponent,
        QuestionCategoryPulseDetailComponent,
        QuestionCategoryPulseUpdateComponent,
        QuestionCategoryPulseDeleteDialogComponent,
        QuestionCategoryPulseDeletePopupComponent
    ],
    entryComponents: [
        QuestionCategoryPulseComponent,
        QuestionCategoryPulseUpdateComponent,
        QuestionCategoryPulseDeleteDialogComponent,
        QuestionCategoryPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppQuestionCategoryPulseModule {}
