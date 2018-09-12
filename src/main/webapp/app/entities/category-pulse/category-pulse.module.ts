import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    CategoryPulseComponent,
    CategoryPulseDetailComponent,
    CategoryPulseUpdateComponent,
    CategoryPulseDeletePopupComponent,
    CategoryPulseDeleteDialogComponent,
    categoryRoute,
    categoryPopupRoute
} from './';

const ENTITY_STATES = [...categoryRoute, ...categoryPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoryPulseComponent,
        CategoryPulseDetailComponent,
        CategoryPulseUpdateComponent,
        CategoryPulseDeleteDialogComponent,
        CategoryPulseDeletePopupComponent
    ],
    entryComponents: [
        CategoryPulseComponent,
        CategoryPulseUpdateComponent,
        CategoryPulseDeleteDialogComponent,
        CategoryPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppCategoryPulseModule {}
