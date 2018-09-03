/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrgAdminPulseUpdateComponent } from 'app/entities/org-admin-pulse/org-admin-pulse-update.component';
import { OrgAdminPulseService } from 'app/entities/org-admin-pulse/org-admin-pulse.service';
import { OrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';

describe('Component Tests', () => {
    describe('OrgAdminPulse Management Update Component', () => {
        let comp: OrgAdminPulseUpdateComponent;
        let fixture: ComponentFixture<OrgAdminPulseUpdateComponent>;
        let service: OrgAdminPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrgAdminPulseUpdateComponent]
            })
                .overrideTemplate(OrgAdminPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrgAdminPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrgAdminPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrgAdminPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orgAdmin = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrgAdminPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orgAdmin = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
