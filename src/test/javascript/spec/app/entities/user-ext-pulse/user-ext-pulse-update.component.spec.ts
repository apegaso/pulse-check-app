/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { UserExtPulseUpdateComponent } from 'app/entities/user-ext-pulse/user-ext-pulse-update.component';
import { UserExtPulseService } from 'app/entities/user-ext-pulse/user-ext-pulse.service';
import { UserExtPulse } from 'app/shared/model/user-ext-pulse.model';

describe('Component Tests', () => {
    describe('UserExtPulse Management Update Component', () => {
        let comp: UserExtPulseUpdateComponent;
        let fixture: ComponentFixture<UserExtPulseUpdateComponent>;
        let service: UserExtPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [UserExtPulseUpdateComponent]
            })
                .overrideTemplate(UserExtPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserExtPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserExtPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserExtPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userExt = entity;
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
                    const entity = new UserExtPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userExt = entity;
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
