/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrganizationPulseUpdateComponent } from 'app/entities/organization-pulse/organization-pulse-update.component';
import { OrganizationPulseService } from 'app/entities/organization-pulse/organization-pulse.service';
import { OrganizationPulse } from 'app/shared/model/organization-pulse.model';

describe('Component Tests', () => {
    describe('OrganizationPulse Management Update Component', () => {
        let comp: OrganizationPulseUpdateComponent;
        let fixture: ComponentFixture<OrganizationPulseUpdateComponent>;
        let service: OrganizationPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrganizationPulseUpdateComponent]
            })
                .overrideTemplate(OrganizationPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrganizationPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganizationPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrganizationPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.organization = entity;
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
                    const entity = new OrganizationPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.organization = entity;
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
