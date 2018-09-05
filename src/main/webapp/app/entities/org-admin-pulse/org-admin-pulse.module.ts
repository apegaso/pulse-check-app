import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    OrgAdminPulseComponent,
    OrgAdminPulseDetailComponent,
    OrgAdminPulseUpdateComponent,
    OrgAdminPulseDeletePopupComponent,
    OrgAdminPulseDeleteDialogComponent,
    orgAdminRoute,
    orgAdminPopupRoute
} from './';

const ENTITY_STATES = [...orgAdminRoute, ...orgAdminPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrgAdminPulseComponent,
        OrgAdminPulseDetailComponent,
        OrgAdminPulseUpdateComponent,
        OrgAdminPulseDeleteDialogComponent,
        OrgAdminPulseDeletePopupComponent
    ],
    entryComponents: [
        OrgAdminPulseComponent,
        OrgAdminPulseUpdateComponent,
        OrgAdminPulseDeleteDialogComponent,
        OrgAdminPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppOrgAdminPulseModule {}
