<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/9/2016
 * Time: 1:57 AM
 */

namespace App\Trans;


abstract class BasicTransformer
{
    public function collection(array $data) {
        return array_map([$this, 'transform'], $data);
    }


    abstract public function transform($data);
}