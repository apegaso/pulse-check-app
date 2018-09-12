/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryPulseUpdateComponent } from 'app/entities/category-pulse/category-pulse-update.component';
import { CategoryPulseService } from 'app/entities/category-pulse/category-pulse.service';
import { CategoryPulse } from 'app/shared/model/category-pulse.model';

describe('Component Tests', () => {
    describe('CategoryPulse Management Update Component', () => {
        let comp: CategoryPulseUpdateComponent;
        let fixture: ComponentFixture<CategoryPulseUpdateComponent>;
        let service: CategoryPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryPulseUpdateComponent]
            })
                .overrideTemplate(CategoryPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoryPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CategoryPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.category = entity;
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
                    const entity = new CategoryPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.category = entity;
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
