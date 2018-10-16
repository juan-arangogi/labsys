/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { ProcedimientoLabsysUpdateComponent } from 'app/entities/procedimiento-labsys/procedimiento-labsys-update.component';
import { ProcedimientoLabsysService } from 'app/entities/procedimiento-labsys/procedimiento-labsys.service';
import { ProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';

describe('Component Tests', () => {
    describe('ProcedimientoLabsys Management Update Component', () => {
        let comp: ProcedimientoLabsysUpdateComponent;
        let fixture: ComponentFixture<ProcedimientoLabsysUpdateComponent>;
        let service: ProcedimientoLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [ProcedimientoLabsysUpdateComponent]
            })
                .overrideTemplate(ProcedimientoLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProcedimientoLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedimientoLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProcedimientoLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.procedimiento = entity;
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
                    const entity = new ProcedimientoLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.procedimiento = entity;
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
