using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TemplateBuilder
{
  class IllegalParentException : Exception
  {
    public IllegalParentException(String aMesssage)
      : base() {
    }
  }
}
