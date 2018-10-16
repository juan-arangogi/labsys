/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { RecipienteLabsysUpdateComponent } from 'app/entities/recipiente-labsys/recipiente-labsys-update.component';
import { RecipienteLabsysService } from 'app/entities/recipiente-labsys/recipiente-labsys.service';
import { RecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';

describe('Component Tests', () => {
    describe('RecipienteLabsys Management Update Component', () => {
        let comp: RecipienteLabsysUpdateComponent;
        let fixture: ComponentFixture<RecipienteLabsysUpdateComponent>;
        let service: RecipienteLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [RecipienteLabsysUpdateComponent]
            })
                .overrideTemplate(RecipienteLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipienteLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipienteLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecipienteLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipiente = entity;
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
                    const entity = new RecipienteLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipiente = entity;
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
