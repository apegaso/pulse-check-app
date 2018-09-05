import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    CategoryLevelPulseComponent,
    CategoryLevelPulseDetailComponent,
    CategoryLevelPulseUpdateComponent,
    CategoryLevelPulseDeletePopupComponent,
    CategoryLevelPulseDeleteDialogComponent,
    categoryLevelRoute,
    categoryLevelPopupRoute
} from './';

const ENTITY_STATES = [...categoryLevelRoute, ...categoryLevelPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoryLevelPulseComponent,
        CategoryLevelPulseDetailComponent,
        CategoryLevelPulseUpdateComponent,
        CategoryLevelPulseDeleteDialogComponent,
        CategoryLevelPulseDeletePopupComponent
    ],
    entryComponents: [
        CategoryLevelPulseComponent,
        CategoryLevelPulseUpdateComponent,
        CategoryLevelPulseDeleteDialogComponent,
        CategoryLevelPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppCategoryLevelPulseModule {}
