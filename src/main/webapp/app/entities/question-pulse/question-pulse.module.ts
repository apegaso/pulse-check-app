import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    QuestionPulseComponent,
    QuestionPulseDetailComponent,
    QuestionPulseUpdateComponent,
    QuestionPulseDeletePopupComponent,
    QuestionPulseDeleteDialogComponent,
    questionRoute,
    questionPopupRoute
} from './';

const ENTITY_STATES = [...questionRoute, ...questionPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionPulseComponent,
        QuestionPulseDetailComponent,
        QuestionPulseUpdateComponent,
        QuestionPulseDeleteDialogComponent,
        QuestionPulseDeletePopupComponent
    ],
    entryComponents: [
        QuestionPulseComponent,
        QuestionPulseUpdateComponent,
        QuestionPulseDeleteDialogComponent,
        QuestionPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppQuestionPulseModule {}
