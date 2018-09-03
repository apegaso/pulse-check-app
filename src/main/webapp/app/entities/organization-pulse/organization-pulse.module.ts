import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    OrganizationPulseComponent,
    OrganizationPulseDetailComponent,
    OrganizationPulseUpdateComponent,
    OrganizationPulseDeletePopupComponent,
    OrganizationPulseDeleteDialogComponent,
    organizationRoute,
    organizationPopupRoute
} from './';

const ENTITY_STATES = [...organizationRoute, ...organizationPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrganizationPulseComponent,
        OrganizationPulseDetailComponent,
        OrganizationPulseUpdateComponent,
        OrganizationPulseDeleteDialogComponent,
        OrganizationPulseDeletePopupComponent
    ],
    entryComponents: [
        OrganizationPulseComponent,
        OrganizationPulseUpdateComponent,
        OrganizationPulseDeleteDialogComponent,
        OrganizationPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppOrganizationPulseModule {}
