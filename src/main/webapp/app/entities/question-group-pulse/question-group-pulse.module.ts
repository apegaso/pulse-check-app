import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    QuestionGroupPulseComponent,
    QuestionGroupPulseDetailComponent,
    QuestionGroupPulseUpdateComponent,
    QuestionGroupPulseDeletePopupComponent,
    QuestionGroupPulseDeleteDialogComponent,
    questionGroupRoute,
    questionGroupPopupRoute
} from './';

const ENTITY_STATES = [...questionGroupRoute, ...questionGroupPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionGroupPulseComponent,
        QuestionGroupPulseDetailComponent,
        QuestionGroupPulseUpdateComponent,
        QuestionGroupPulseDeleteDialogComponent,
        QuestionGroupPulseDeletePopupComponent
    ],
    entryComponents: [
        QuestionGroupPulseComponent,
        QuestionGroupPulseUpdateComponent,
        QuestionGroupPulseDeleteDialogComponent,
        QuestionGroupPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppQuestionGroupPulseModule {}
