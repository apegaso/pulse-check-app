import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import { PulseCheckAppAdminModule } from 'app/admin/admin.module';
import {
    UserExtPulseComponent,
    UserExtPulseDetailComponent,
    UserExtPulseUpdateComponent,
    UserExtPulseDeletePopupComponent,
    UserExtPulseDeleteDialogComponent,
    userExtRoute,
    userExtPopupRoute
} from './';

const ENTITY_STATES = [...userExtRoute, ...userExtPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, PulseCheckAppAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserExtPulseComponent,
        UserExtPulseDetailComponent,
        UserExtPulseUpdateComponent,
        UserExtPulseDeleteDialogComponent,
        UserExtPulseDeletePopupComponent
    ],
    entryComponents: [
        UserExtPulseComponent,
        UserExtPulseUpdateComponent,
        UserExtPulseDeleteDialogComponent,
        UserExtPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppUserExtPulseModule {}
